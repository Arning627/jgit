package cn.arning.jgit.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author xnguo
 * @date 2021/12/2 下午3:52
 */
@Configuration
@EnableAsync
public class ThreadPoolConfig {


    @Bean
    public Executor asyncClone() {

        int coreSize = Runtime.getRuntime().availableProcessors();

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setMaxPoolSize(coreSize * 2);
        executor.setCorePoolSize(coreSize);
        executor.setKeepAliveSeconds(60);
        executor.setQueueCapacity(400);
        executor.setThreadNamePrefix("ASYNC-CLONE");

        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());

        executor.initialize();
        return executor;
    }


}
