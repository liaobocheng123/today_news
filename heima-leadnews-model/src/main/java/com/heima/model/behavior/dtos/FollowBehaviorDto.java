package com.heima.model.behavior.dtos;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class FollowBehaviorDto {
    //文章id
    @JsonSerialize(using= ToStringSerializer.class)
    Long articleId;
    //关注的id
    Integer followId;
    //用户id
    Integer userId;
}