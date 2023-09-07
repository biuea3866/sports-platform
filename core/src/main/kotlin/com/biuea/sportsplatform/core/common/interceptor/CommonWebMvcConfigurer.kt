package com.biuea.sportsplatform.core.common.interceptor

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CommonWebMvcConfigurer(
    private val httpRequestInterceptor: HttpRequestInterceptor
): WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(httpRequestInterceptor)
            .addPathPatterns("/api/**")
    }
}