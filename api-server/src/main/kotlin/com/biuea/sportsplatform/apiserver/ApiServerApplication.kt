package com.biuea.sportsplatform.apiserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy
import java.util.*
import javax.annotation.PostConstruct

@EnableAspectJAutoProxy
@SpringBootApplication
class ApiServerApplication {
    @PostConstruct
    fun setup() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    }
}

fun main(args: Array<String>) {
    runApplication<ApiServerApplication>(*args)
}
