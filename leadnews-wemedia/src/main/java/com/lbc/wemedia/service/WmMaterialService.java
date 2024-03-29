package com.lbc.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lbc.model.common.dtos.ResponseResult;
import com.lbc.model.wemedia.dtos.WmMaterialDto;
import com.lbc.model.wemedia.pojos.WmMaterial;
import org.springframework.web.multipart.MultipartFile;

public interface WmMaterialService extends IService<WmMaterial> {

    /**
     * 上传图片接口
     * @param multipartFile
     * @return
     */
    ResponseResult uploadPicture(MultipartFile multipartFile);

    /**
     * 素材列表查询
     * @param dto
     * @return
     */
    ResponseResult findList(WmMaterialDto dto);

    /**
     * 删除图片
     * @param id
     * @return
     */
    ResponseResult delPicture(Integer id);

    /**
     * 收藏与取消收藏
     * @param id
     * @param type
     * @return
     */
    ResponseResult updateStatus(Integer id, Short type);

}