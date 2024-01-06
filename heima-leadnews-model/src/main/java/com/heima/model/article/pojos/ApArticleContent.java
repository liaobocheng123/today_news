package com.heima.model.article.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("ap_article_content")
public class ApArticleContent implements Serializable {

    @TableId(value = "id",type = IdType.ID_WORKER)
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /**
     * 文章id
     */
    @TableField("article_id")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long articleId;

    /**
     * 文章内容
     */
    private String content;
}
