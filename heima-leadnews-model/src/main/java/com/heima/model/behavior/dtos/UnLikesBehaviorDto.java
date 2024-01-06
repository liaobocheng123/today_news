package com.heima.model.behavior.dtos;

//import com.heima.model.common.annotation.IdEncrypt;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class UnLikesBehaviorDto {
    // 文章ID
//    @IdEncrypt
    @JsonSerialize(using= ToStringSerializer.class)
    Long articleId;

    /**
     * 不喜欢操作方式
     * 0 不喜欢
     * 1 取消不喜欢
     */
    Short type;

}