package com.biuea.sportsplatform.core.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

@Configuration
@EnableAsync
class AsyncThreadConfiguration {
    @Bean
    fun executor(): Executor {
        val executor = ThreadPoolTaskExecutor()

        executor.corePoolSize = 2
        executor.maxPoolSize = 2
        executor.setQueueCapacity(QUEUE_CAPACITY)
        executor.initialize()

        return executor
    }

    companion object {
        const val QUEUE_CAPACITY = 500
    }
}