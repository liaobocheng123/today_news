package com.lbc.model.article.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
/**
 * <p>
 * APP已发布文章配置表
 * </p>
 *
 * @author itheima
 */

@Data
@TableName("ap_article_config")
public class ApArticleConfig {

    @TableId(value = "id",type = IdType.ID_WORKER)
    private Long id;

    /**
     * 文章id
     */
    @TableField("article_id")
    private Long articleId;

    /**
     * 是否可评论
     */
    @TableField("is_comment")
    private Boolean isComment;

    /**
     * 是否转发
     */
    @TableField("is_forward")
    private Boolean isForward;

    /**
     * 是否下架
     */
    @TableField("is_down")
    private Boolean isDown;

    /**
     * 是否已删除
     */
    @TableField("is_delete")
    private Boolean isDelete;
}