package com.lbc.comment.service.impl;

import com.lbc.comment.feign.UserFeign;
import com.lbc.comment.service.CommentService;
import com.lbc.model.comment.dtos.CommentDto;
import com.lbc.model.comment.dtos.CommentLikeDto;
import com.lbc.model.comment.dtos.CommentSaveDto;
import com.lbc.model.comment.pojos.ApComment;
import com.lbc.model.comment.pojos.ApCommentLike;
import com.lbc.model.comment.vo.ApCommentVo;
import com.lbc.model.common.dtos.ResponseResult;
import com.lbc.model.common.enums.AppHttpCodeEnum;
import com.lbc.model.user.pojos.ApUser;
import com.lbc.utils.threadlocal.AppThreadLocalUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
public class CommentServiceImpl implements CommentService {

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public ResponseResult saveComment(CommentSaveDto dto) {
        //1.检查参数
        if (dto.getArticleId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        if (dto.getContent() != null && dto.getContent().length() > 120) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE, "评论内容不能超过120字");
        }

        //2.判断是否登录
        ApUser user = AppThreadLocalUtils.getUser();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        //3.安全过滤,判断输入的内容是否涉黑涉黄，自行调用aliyun检测实现，可以仿照作者发布文档时的功能实现

        //4.保存评论
        ApUser apUser = userFeign.findUserById(user.getId());
        if (apUser == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE, "当前登录信息有误");
        }
        ApComment apComment = new ApComment();
        apComment.setAuthorId(apUser.getId());
        apComment.setAuthorName(apUser.getName());
        apComment.setContent(dto.getContent());
        apComment.setEntryId(dto.getArticleId());
        apComment.setCreatedTime(new Date());
        apComment.setUpdatedTime(new Date());
        apComment.setImage(apUser.getImage());
        apComment.setLikes(0);
        apComment.setReply(0);
        apComment.setType((short) 0);
        apComment.setFlag((short) 0);
        mongoTemplate.insert(apComment);
        System.out.println("app评论文字成功");

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 点赞或取消点赞
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult like(CommentLikeDto dto) {
        //1.检查参数
        if (dto.getCommentId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //2.判断是否登录
        ApUser user = AppThreadLocalUtils.getUser();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        ApComment apComment = mongoTemplate.findById(dto.getCommentId(), ApComment.class);
        if (apComment == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "当前评论没找到");
        }

        //3.点赞的操作
        if (dto.getOperation() == 0) {
            //更新评论的点赞数量
            apComment.setLikes(apComment.getLikes() + 1);
            mongoTemplate.save(apComment);

            //新增评论点赞的数据
            ApCommentLike apCommentLike = new ApCommentLike();
            apCommentLike.setAuthorId(user.getId());
            apCommentLike.setCommentId(apComment.getId());
            mongoTemplate.save(apCommentLike);

        } else {
            //4.取消点赞的操作
            //更新评论的点赞数量
            apComment.setLikes(apComment.getLikes() <= 0 ? 0 : apComment.getLikes() - 1);
            mongoTemplate.save(apComment);

            //删除评论点赞的数据
            mongoTemplate.remove(Query.query(Criteria.where("authorId").is(user.getId())
                    .and("commentId").is(apComment.getId())), ApCommentLike.class);

        }


        //5.结果封装返回 --> 评论的点赞数量
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("likes", apComment.getLikes());
        return ResponseResult.okResult(resultMap);
    }

    /**
     * 查询文章评论列表
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult findByArticleId(CommentDto dto) {
        System.out.println("1、开始查询文章评论列表");
        //1.检查参数
        if (dto.getArticleId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }

        int size = 10;

        //2.按照文章id过滤，设置分页和排序
        //Query query = Query.query(Criteria.where("entryId").is(dto.getArticleId()).and("createdTime").lt(dto.getMinDate()));
        Query query = Query.query(Criteria.where("entryId").is(dto.getArticleId()));
        query.limit(size).with(Sort.by(Sort.Direction.DESC, "createdTime"));
        List<ApComment> list = mongoTemplate.find(query, ApComment.class);
        System.out.println("2、完成mongodb查询文章评论列表:"+ list.size());

        //3.数据封装返回
        //3.1 用户未登录 加载数据
        ApUser user = AppThreadLocalUtils.getUser();
        if (user == null) {
            return ResponseResult.okResult(list);
        }

        //3.2 用户已登录，加载数据，需要判断当前用户点赞了哪些评论
        List<String> idList = list.stream().map(x -> x.getId()).collect(Collectors.toList());
        Query query1 = Query.query(Criteria.where("commentId").in(idList).and("authorId").is(user.getId()));
        List<ApCommentLike> apCommentLikes = mongoTemplate.find(query1, ApCommentLike.class);

        System.out.println("3、完成mongodb查询文章评论点赞详细");

        List<ApCommentVo> resultList = new ArrayList<>();

        if (list != null && apCommentLikes != null) {
            list.stream().forEach(x -> {
                ApCommentVo apCommentVo = new ApCommentVo();
                BeanUtils.copyProperties(x, apCommentVo);
                for (ApCommentLike apCommentLike : apCommentLikes) {
                    if (x.getId().equals(apCommentLike.getCommentId())) {
                        apCommentVo.setOperation((short) 0);
                        break;
                    }
                }
                resultList.add(apCommentVo);
            });

            return ResponseResult.okResult(resultList);
        } else {
            return ResponseResult.okResult(list);
        }
    }
}
