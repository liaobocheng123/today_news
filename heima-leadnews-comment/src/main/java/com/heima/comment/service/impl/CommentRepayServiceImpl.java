package com.heima.comment.service.impl;

import com.heima.comment.feign.UserFeign;
import com.heima.comment.service.CommentRepayService;
import com.heima.model.comment.dtos.CommentRepayDto;
import com.heima.model.comment.dtos.CommentRepayLikeDto;
import com.heima.model.comment.dtos.CommentRepaySaveDto;
import com.heima.model.comment.pojos.ApComment;
import com.heima.model.comment.pojos.ApCommentLike;
import com.heima.model.comment.pojos.ApCommentRepay;
import com.heima.model.comment.pojos.ApCommentRepayLike;
import com.heima.model.comment.vo.ApCommentRepayVo;
import com.heima.model.comment.vo.ApCommentVo;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.user.pojos.ApUser;
import com.heima.utils.threadlocal.AppThreadLocalUtils;
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
public class CommentRepayServiceImpl implements CommentRepayService {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 加载评论回复列表
     * @param dto
     * @return
     */
    @Override
    public ResponseResult loadCommentRepay(CommentRepayDto dto) {
        //1.检查参数
        if(dto.getCommentId() == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        int size = 10;

        //2.根据文章id过滤查询,设置分页和排序
        //Query query = Query.query(Criteria.where("commentId").is(dto.getCommentId()).and("createdTime").lt(dto.getMinDate()));
        Query query = Query.query(Criteria.where("commentId").is(dto.getCommentId()));
        query.limit(size).with(Sort.by(Sort.Direction.DESC,"createdTime"));
        List<ApCommentRepay> apCommentRepays = mongoTemplate.find(query, ApCommentRepay.class);

        //3.用户未登录，直接返回数据
        ApUser user = AppThreadLocalUtils.getUser();
        if(user == null){
            return ResponseResult.okResult(apCommentRepays);
        }

        //4.用户已登录，需要检索当前用户点赞了哪些评论

        //4.1查询点赞列表  userid和评论id
        List<String> idList = apCommentRepays.stream().map(x -> x.getId()).collect(Collectors.toList());
        List<ApCommentRepayLike> apCommentRepayLikes = mongoTemplate.find(Query.query(Criteria.where("authorId").is(user.getId()).and("commentRepayId").in(idList)), ApCommentRepayLike.class);

        //4.2 判断当前评论哪些被点赞了

        List<ApCommentRepayVo> commentVoList = new ArrayList<>();

        if(apCommentRepayLikes != null){
            apCommentRepays.stream().forEach(commentRepay->{
                ApCommentRepayVo vo = new ApCommentRepayVo();
                BeanUtils.copyProperties(commentRepay,vo);
                for (ApCommentRepayLike apCommentLike : apCommentRepayLikes) {
                    if(commentRepay.getId().equals(apCommentLike.getCommentRepayId())){
                        vo.setOperation((short)0);
                        break;
                    }
                }
                commentVoList.add(vo);
            });

            return ResponseResult.okResult(commentVoList);

        }else {
            return ResponseResult.okResult(apCommentRepays);
        }
    }

    @Autowired
    private UserFeign userFeign;

    /**
     * 保存回复内容
     * @param dto
     * @return
     */
    @Override
    public ResponseResult saveCommentRepay(CommentRepaySaveDto dto) {
        //1.检查参数
        if (dto.getCommentId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        if (dto.getContent() != null && dto.getContent().length() > 140) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE, "评论内容不能超过140字");
        }

        //2.判断是否登录
        ApUser user = AppThreadLocalUtils.getUser();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        //3.安全过滤,自行实现

        //4.保存评论
        ApUser apUser = userFeign.findUserById(user.getId());
        if (apUser == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE, "当前登录信息有误");
        }
        ApCommentRepay apCommentRepay = new ApCommentRepay();
        apCommentRepay.setCommentId(dto.getCommentId());
        apCommentRepay.setAuthorId(user.getId());
        apCommentRepay.setAuthorName(apUser.getName());
        apCommentRepay.setContent(dto.getContent());
        apCommentRepay.setLikes(0);
        apCommentRepay.setUpdatedTime(new Date());
        apCommentRepay.setCreatedTime(new Date());

        mongoTemplate.insert(apCommentRepay);

        //更新评论的回复数量
        ApComment apComment = mongoTemplate.findById(dto.getCommentId(), ApComment.class);
        apComment.setReply(apComment.getReply()+1);
        mongoTemplate.save(apComment);



        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 点赞回复内容
     * @param dto
     * @return
     */
    @Override
    public ResponseResult saveCommentRepayLike(CommentRepayLikeDto dto) {
        //1.检查参数
        if (dto.getCommentRepayId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //2.判断是否登录
        ApUser user = AppThreadLocalUtils.getUser();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        ApCommentRepay apCommentRepay = mongoTemplate.findById(dto.getCommentRepayId(), ApCommentRepay.class);
        if (apCommentRepay == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "当前评论没找到");
        }

        //3.点赞的操作
        if (dto.getOperation() == 0) {
            //更新评论的点赞数量
            apCommentRepay.setLikes(apCommentRepay.getLikes() + 1);
            mongoTemplate.save(apCommentRepay);

            //新增评论点赞的数据
            ApCommentRepayLike apCommentRepayLike = new ApCommentRepayLike();
            apCommentRepayLike.setAuthorId(user.getId());
            apCommentRepayLike.setCommentRepayId(apCommentRepay.getId());
            mongoTemplate.save(apCommentRepayLike);

        } else {
            //4.取消点赞的操作
            //更新评论的点赞数量
            apCommentRepay.setLikes(apCommentRepay.getLikes() <= 0 ? 0 : apCommentRepay.getLikes() - 1);
            mongoTemplate.save(apCommentRepay);

            //删除评论点赞的数据
            mongoTemplate.remove(Query.query(Criteria.where("authorId").is(user.getId())
                    .and("commentRepayId").is(apCommentRepay.getId())),ApCommentRepayLike.class);

        }


        //5.结果封装返回 --> 评论的点赞数量
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("likes",apCommentRepay.getLikes());
        return ResponseResult.okResult(resultMap);
    }
}
