package com.heima.apis.user;

import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.AuthDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "认证录管理", tags = "user", description = "认证录管理API")
public interface ApUserRealnameControllerApi {

    /**
     *按照状态查询用户认证列表
     * @param dto
     * @return
     */
    @ApiOperation("按照状态查询用户认证列表")
    public ResponseResult loadListByStatus(AuthDto dto);


    /**
     * 审核通过
     * @param dto
     * @return
     */
    @ApiOperation("审核通过")
    public ResponseResult authPass(AuthDto dto) ;

    /**
     * 审核失败
     * @param dto
     * @return
     */
    @ApiOperation("审核失败")
    public ResponseResult authFail(AuthDto dto);
}