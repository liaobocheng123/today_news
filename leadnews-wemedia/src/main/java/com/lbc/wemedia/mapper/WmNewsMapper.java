package com.lbc.wemedia.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lbc.model.admin.dtos.NewsAuthDto;
import com.lbc.model.wemedia.pojos.WmNews;
import com.lbc.model.wemedia.vo.WmNewsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WmNewsMapper extends BaseMapper<WmNews> {
    List<WmNewsVo> findListAndPage(@Param("dto") NewsAuthDto dto);
    int findListCount(@Param("dto") NewsAuthDto dto);
}