package com.heima.model.article.dtos;

//import com.heima.model.common.annotation.IdEncrypt;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class ArticleInfoDto {
    // 设备ID
//    @IdEncrypt
    @JsonSerialize(using= ToStringSerializer.class)
    Integer equipmentId;
    // 文章ID
//    @IdEncrypt
    @JsonSerialize(using= ToStringSerializer.class)
    Long articleId;
    // 作者ID
//    @IdEncrypt
    @JsonSerialize(using= ToStringSerializer.class)
    Integer authorId;
}