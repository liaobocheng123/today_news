package com.heima.model.comment.dtos;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.heima.model.common.dtos.PageRequestDto;
import lombok.Data;

@Data
public class CommentManageDto extends PageRequestDto {

    /**
     * 文章id
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long articleId;
}
