package com.biuea.sportsplatform.core.common.config

import org.springframework.amqp.rabbit.connection.AbstractConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfiguration {
    @Bean
    fun rabbitTemplate(connectionFactory: AbstractConnectionFactory): RabbitTemplate {
        val template = RabbitTemplate(connectionFactory)
        template.messageConverter = jsonMessageConverter()

        return template
    }

    @Bean
    fun jsonMessageConverter(): MessageConverter {
        return Jackson2JsonMessageConverter()
    }
}