package com.lbc.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lbc.article.mapper.ApArticleContentMapper;
import com.lbc.article.service.ApArticleContentService;
import com.lbc.model.article.pojos.ApArticleContent;
import org.springframework.stereotype.Service;

@Service
public class ApArticleContentServiceImpl extends ServiceImpl<ApArticleContentMapper,ApArticleContent> implements ApArticleContentService {

}