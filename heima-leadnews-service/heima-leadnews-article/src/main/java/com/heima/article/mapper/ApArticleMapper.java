package com.heima.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.article.pojos.ApArticle;
import lombok.Data;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Mapper
public interface ApArticleMapper extends BaseMapper<ApArticle> {
    /**
     * 加载文章列表
     * @param dto
     * @param type
     * @return
     */
    List<ApArticle> loadArticleList(ArticleHomeDto dto, Short type);

    /**
     * 查询前五天的文章数据
     */
    List<ApArticle> findArticleByLast5days(@Param("dayParam") LocalDate dayParam);
}
