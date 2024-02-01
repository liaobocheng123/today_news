package com.lbc.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lbc.article.mapper.ApArticleConfigMapper;
import com.lbc.article.service.ApArticleConfigService;
import com.lbc.model.article.pojos.ApArticleConfig;
import org.springframework.stereotype.Service;

@Service
public class ApArticleConfigServiceImpl extends ServiceImpl<ApArticleConfigMapper, ApArticleConfig> implements ApArticleConfigService {
}