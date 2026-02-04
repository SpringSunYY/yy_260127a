package com.lz.module.infra.framework.file.config;

import com.lz.module.infra.framework.file.core.client.FileClientFactory;
import com.lz.module.infra.framework.file.core.client.FileClientFactoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文件配置类
 *
 * @author YY
 */
@Configuration(proxyBeanMethods = false)
public class ProjectFileAutoConfiguration {

    @Bean
    public FileClientFactory fileClientFactory() {
        return new FileClientFactoryImpl();
    }

}
