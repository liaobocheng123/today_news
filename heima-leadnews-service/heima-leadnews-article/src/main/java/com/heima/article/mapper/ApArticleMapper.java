package com.heima.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.article.pojos.ApArticle;

import java.util.List;

public interface ApArticleMapper extends BaseMapper<ApArticle> {
    List<ApArticle> loadArticleList(ArticleHomeDto dto, Short type);
}
