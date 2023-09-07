package com.biuea.sportsplatform.core.common.config

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Configuration
class QueryDslConfiguration(
    @PersistenceContext
    private val entityManager: EntityManager
) {
    @Bean
    fun jpaQueryFactory() = JPAQueryFactory(entityManager)
}