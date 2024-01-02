package com.heima.model.admin.dtos;

import com.heima.model.common.dtos.PageRequestDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.checkerframework.checker.units.qual.A;

/**
 * @author mrchen
 * @date 2022/4/21 11:23
 */
@Data
public class ChannelDTO extends PageRequestDto {
    /**
     * 频道名称
     */
    @ApiModelProperty("频道名称")
    private String name ;
    /**
     * 状态
     */
    @ApiModelProperty("频道状态  开启: 1     关闭: 0")
    private Integer status;
}
