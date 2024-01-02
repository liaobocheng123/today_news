package com.heima.wemedia.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heima.model.wemedia.dtos.AuthDTO;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.model.wemedia.vo.WmNewsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface WmNewsMapper  extends BaseMapper<WmNews> {
    List<WmNewsVo> findListAndPage(@Param("dto") AuthDTO dto);

    int findListCount(@Param("dto") AuthDTO dto);
}
