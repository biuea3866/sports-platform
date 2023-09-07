package com.biuea.sportsplatform.core.common.config


import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.support.TransactionSynchronizationManager
import javax.sql.DataSource


@Configuration
@EnableAutoConfiguration(exclude = [DataSourceAutoConfiguration::class])
@EnableTransactionManagement
@Profile("release")
class MySQLConfiguration {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.write")
    fun writeDataSource(): DataSource {
        return DataSourceBuilder
            .create()
            .type(HikariDataSource::class.java)
            .build()
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.read")
    fun readDataSource(): DataSource {
        return DataSourceBuilder
            .create()
            .type(HikariDataSource::class.java)
            .build()
    }

    @Bean
    fun routingDataSource(
        @Qualifier("writeDataSource") writeDataSource: DataSource,
        @Qualifier("readDataSource") readDataSource: DataSource
    ): DataSource {
        val routingDataSource = MySQLRoutingDataSource()

        val dataSourceMap = mapOf<Any, Any>(
            MySQLRoutingKey.WRITE to writeDataSource,
            MySQLRoutingKey.READ to readDataSource
        )
        routingDataSource.setTargetDataSources(dataSourceMap)
        routingDataSource.setDefaultTargetDataSource(writeDataSource)

        return routingDataSource
    }

    @Primary
    @Bean
    fun dataSource(
        @Qualifier("routingDataSource") routingDataSource: DataSource
    ): DataSource {
        return LazyConnectionDataSourceProxy(routingDataSource)
    }
}

class MySQLRoutingDataSource : AbstractRoutingDataSource() {
    override fun determineCurrentLookupKey(): Any {
        return if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            MySQLRoutingKey.READ
        } else {
            MySQLRoutingKey.WRITE
        }
    }
}
enum class MySQLRoutingKey {
    WRITE,
    READ;
}
