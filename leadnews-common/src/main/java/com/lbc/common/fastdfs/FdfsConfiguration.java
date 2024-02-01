package com.lbc.common.fastdfs;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * 配置后等于把连接信息和fastdfs包合在了一起
 */
@Configuration
@Import(FdfsClientConfig.class) // 导入FastDFS-Client组件
@PropertySource("classpath:fast_dfs.properties")
public class FdfsConfiguration {
}