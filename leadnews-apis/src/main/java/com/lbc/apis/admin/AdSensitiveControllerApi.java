package com.lbc.apis.admin;

import com.lbc.model.admin.dtos.SensitiveDto;
import com.lbc.model.admin.pojos.AdSensitive;
import com.lbc.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "敏感词管理", tags = "sensitive", description = "敏感词管理API")
public interface AdSensitiveControllerApi {

    /**
     * 根据名称分页查询敏感词
     * @param dto
     * @return
     */
    @ApiOperation("根据名称分页查询敏感词列表")
    public ResponseResult list(SensitiveDto dto);

    /**
     * 新增
     * @param adSensitive
     * @return
     */
    @ApiOperation("新增敏感词")
    public ResponseResult save(AdSensitive adSensitive);

    /**
     * 修改
     * @param adSensitive
     * @return
     */
    @ApiOperation("修改敏感词")
    public ResponseResult update(AdSensitive adSensitive);

    /**
     * 删除敏感词
     * @param id
     * @return
     */
    @ApiOperation("删除敏感词")
    public ResponseResult deleteById(Integer id);
}