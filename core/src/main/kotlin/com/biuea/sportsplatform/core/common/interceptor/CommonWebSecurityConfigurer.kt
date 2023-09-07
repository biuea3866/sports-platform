package com.biuea.sportsplatform.core.common.interceptor

import com.biuea.sportsplatform.core.common.config.ServerEnvironment
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.CorsUtils
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@EnableWebSecurity
@Configuration
class CommonWebSecurityConfigurer(
    @Value("\${server.env}")
    val env: ServerEnvironment
): WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        when (env) {
            ServerEnvironment.LOCAL -> http.cors().and().csrf().disable().httpBasic()
            else -> http.authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/api/**").authenticated()
                .anyRequest().permitAll().and()
                .cors().and()
                .csrf().disable().httpBasic()
        }
    }

    @Bean
    @Suppress("LongMethod")
    fun corsConfigurationSource(): CorsConfigurationSource {
        val source = UrlBasedCorsConfigurationSource()

        source.registerCorsConfiguration(
            "/api/**",
            atsCorsConfiguration()
        )

        return source
    }

    private fun atsCorsConfiguration(): CorsConfiguration {
        val configuration = CorsConfiguration()

        when (env) {
            ServerEnvironment.LOCAL -> {
                configuration.addAllowedOriginPattern("*")
                configuration.addAllowedHeader("*")
                configuration.addAllowedMethod("*")
                configuration.allowCredentials = true
            }

            ServerEnvironment.DEV -> {
                configuration.addAllowedHeader("*")
                configuration.addAllowedMethod("*")
                configuration.addAllowedOriginPattern("*")
                configuration.allowCredentials = true
            }

            ServerEnvironment.STAGE -> {
                configuration.addAllowedOriginPattern("*")
                configuration.addAllowedHeader("*")
                configuration.addAllowedMethod("*")
                configuration.allowCredentials = true
            }

            ServerEnvironment.PROD -> {
                configuration.addAllowedOriginPattern("*")
                configuration.addAllowedHeader("*")
                configuration.addAllowedMethod("*")
                configuration.allowCredentials = true
            }
        }

        return configuration
    }
}