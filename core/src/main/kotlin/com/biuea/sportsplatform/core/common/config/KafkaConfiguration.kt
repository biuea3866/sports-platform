package com.biuea.sportsplatform.core.common.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.config.SaslConfigs
import org.apache.kafka.common.serialization.Serializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.kafka.support.converter.JsonMessageConverter

@EnableKafka
@Configuration
class KafkaConfiguration {
    @Value("\${spring.kafka.bootstrap-servers}")
    lateinit var bootstrapServer: String

    @Value("\${spring.kafka.properties.security.protocol}")
    lateinit var securityProtocol: String

    @Value("\${spring.kafka.properties.sasl.mechanism}")
    lateinit var saslMechanism: String

    @Value("\${spring.kafka.properties.sasl.jaas.config}")
    lateinit var saslJaasConfig: String

    @Bean
    fun <T> producerFactory(): ProducerFactory<String, Any> {
        val config = mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to this.bootstrapServer,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.qualifiedName,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to KafkaSerializer<T>()::class.java,
            CommonClientConfigs.SECURITY_PROTOCOL_CONFIG to this.securityProtocol,
            SaslConfigs.SASL_MECHANISM to this.saslMechanism,
            SaslConfigs.SASL_JAAS_CONFIG to this.saslJaasConfig,
        )

        return DefaultKafkaProducerFactory(config)
    }

    @Bean
    fun <T> kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, Any> {
        val config = mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to this.bootstrapServer,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.qualifiedName,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.qualifiedName,
            ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to false,
            CommonClientConfigs.SECURITY_PROTOCOL_CONFIG to this.securityProtocol,
            SaslConfigs.SASL_MECHANISM to this.saslMechanism,
            SaslConfigs.SASL_JAAS_CONFIG to this.saslJaasConfig,
        )
        val consumerFactory = DefaultKafkaConsumerFactory<String, Any>(config)

        val factory = ConcurrentKafkaListenerContainerFactory<String, Any>()
        factory.setMessageConverter(JsonMessageConverter())
        factory.consumerFactory = consumerFactory
        factory.setConcurrency(1)
        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL

        return factory
    }

    @Bean
    fun <T> kafkaTemplate(): KafkaTemplate<String, Any> {
        return KafkaTemplate(producerFactory<T>())
    }
}

class KafkaSerializer<T> : Serializer<T> {
    override fun serialize(topic: String?, data: T?): ByteArray {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())

        return objectMapper.writeValueAsBytes(data)
    }
}