package com.heima.apis.wemedia;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.pojos.WmUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "自媒体端管理", tags = "WmUser", description = "自媒体端管理API")
public interface WmUserControllerApi {

    /**
     * 保存自媒体用户
     * @param wmUser
     * @return
     */
    @ApiOperation("保存自媒体用户")
    public ResponseResult save(WmUser wmUser);

    /**
     * 按照名称查询用户
     * @param name
     * @return
     */
    @ApiOperation("按照名称查询用户")
    public WmUser findByName(String name);


    /**
     * 根据id查询
     * @param id
     * @return
     */
    WmUser findWmUserById( Integer id);
}