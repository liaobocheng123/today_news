package com.heima.model.article.dtos;

//import com.heima.model.common.annotation.IdEncrypt;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.Date;

@Data
public class CollectionBehaviorDto {

    // 文章、动态ID
//    @IdEncrypt
    @JsonSerialize(using= ToStringSerializer.class)
    Long entryId;
    /**
     * 收藏内容类型
     * 0 文章
     * 1 动态
     */
    Short type;

    /**
     * 操作类型
     * 0收藏
     * 1取消收藏
     */
    Short operation;

    Date publishedTime;

}