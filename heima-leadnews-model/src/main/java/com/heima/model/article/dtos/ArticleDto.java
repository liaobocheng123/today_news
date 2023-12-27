package com.heima.model.article.dtos;

import com.heima.model.article.pojos.ApArticle;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleDto extends ApArticle {
    private String content;//文章内容
}
