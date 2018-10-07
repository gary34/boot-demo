package com.gary.demo.config;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 平台名-子平台名-模块名:
 * <p>
 * [注释信息]
 *
 * @author gary.pu  2018-10-06
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    //    @Bean
    //    MappingJackson2HttpMessageConverter converter() {
    //        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    //        converter.getObjectMapper().disable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
    //        converter.setObjectMapper(new ObjectMapper(){
    //
    //        });
    //        return converter;
    //    }
    //
    //    @Override
    //    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    //        converters.add(0, converter());
    //    }
    /**
     * 配置线程池
     * @return
     */
    @Bean(name = "asyncPoolTaskExecutor")
    public ThreadPoolTaskExecutor getAsyncThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(20);
        taskExecutor.setMaxPoolSize(200);
        taskExecutor.setQueueCapacity(25);
        taskExecutor.setKeepAliveSeconds(200);
        taskExecutor.setThreadNamePrefix("callable-");
        // 线程池对拒绝任务（无线程可用）的处理策略，目前只支持AbortPolicy、CallerRunsPolicy；默认为后者
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.initialize();
        return taskExecutor;
    }

    @Override
    public void configureAsyncSupport(final AsyncSupportConfigurer configurer) {
        //处理 callable超时
        configurer.setDefaultTimeout(100);
        configurer.setTaskExecutor(getAsyncThreadPoolTaskExecutor());
    }


    @Bean
    public Jackson2ObjectMapperBuilder jacksonBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();

        //        builder.featuresToDisable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        //        builder.featuresToDisable(SerializationFeature.)
        builder.serializationInclusion(Include.NON_EMPTY);
        builder.serializationInclusion(Include.NON_NULL);
        return builder;
    }

}
