package com.biuea.sportsplatform.core.common.config

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.mongo.MongoProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.convert.converter.Converter
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.core.convert.MongoCustomConversions
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import java.net.URLEncoder
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.*

@Configuration
@EnableMongoRepositories(
    basePackages = ["com.biuea.sportsplatform.*.repository.mongo"],
    mongoTemplateRef = "mongoPrimaryTemplate"
)
class MongoConfiguration {
    @Bean(name = ["mongoPrimaryProperties"])
    @ConfigurationProperties(prefix = "mongodb.primary")
    @Primary
    fun mongoPrimaryProperties(): MongoProperties {
        return MongoProperties()
    }

    @Bean(name = ["mongoPrimaryClient"])
    fun mongoPrimaryClient(@Qualifier("mongoPrimaryProperties") mongoProperties: MongoProperties): MongoClient {
        val uri = "mongodb+srv://${mongoProperties.username}:${
            URLEncoder.encode(
                mongoProperties.password.concatToString(),
                "UTF-8"
            )
        }@${mongoProperties.host}/${mongoProperties.database}" +
                "?retryWrites=true&w=majority&authSource=${mongoProperties.authenticationDatabase}"
        val connectionString = ConnectionString(uri)
        val mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .build()

        return MongoClients.create(mongoClientSettings)
    }

    @Primary
    @Bean(name = ["mongoPrimaryDBFactory"])
    fun mongoPrimaryDBFactory(
        @Qualifier("mongoPrimaryClient") mongoClient: MongoClient,
        @Qualifier("mongoPrimaryProperties") mongoProperties: MongoProperties
    ): MongoDatabaseFactory {
        return SimpleMongoClientDatabaseFactory(
            mongoClient,
            mongoProperties.database
        )
    }

    @Primary
    @Bean(name = ["mongoPrimaryTemplate"])
    fun mongoPrimaryTemplate(
        @Qualifier("mongoPrimaryDBFactory") mongoDatabaseFactory: MongoDatabaseFactory,
        @Qualifier("mongoCustomConversions") mongoCustomConversions: MongoCustomConversions,
    ): MongoTemplate {
        val mongoTemplate = MongoTemplate(mongoDatabaseFactory)
        val converter = mongoTemplate.converter as MappingMongoConverter
        converter.setTypeMapper(DefaultMongoTypeMapper(null))
        converter.setCustomConversions(mongoCustomConversions)
        converter.afterPropertiesSet()

        return mongoTemplate
    }

    @Bean(name = ["mongoCustomConversions"])
    fun mongoCustomConversions(): MongoCustomConversions {
        val converters = mutableListOf<Converter<*, *>>()

        converters.add(ZonedDateTimeReadConverter())
        converters.add(ZonedDateTimeWriteConverter())

        return MongoCustomConversions(converters)
    }
}

class ZonedDateTimeReadConverter : Converter<Date, ZonedDateTime> {
    override fun convert(date: Date): ZonedDateTime {
        return date.toInstant().atZone(ZoneOffset.UTC)
    }

    fun convertNullableDate(date: Date?): ZonedDateTime? {
        return date?.toInstant()?.atZone(ZoneOffset.UTC)
    }
}

class ZonedDateTimeWriteConverter : Converter<ZonedDateTime, Date> {
    override fun convert(source: ZonedDateTime): Date {
        return Date.from(source.toInstant())
    }
}
