package com.biuea.sportsplatform.core.common.exception

import org.springframework.http.HttpStatus

open class BaseException : RuntimeException {
    val statusCode: HttpStatus
    override var message: String? = null

    constructor(statusCode: HttpStatus) : super() {
        this.statusCode = statusCode
    }

    constructor(statusCode: HttpStatus, message: String?) : super(message) {
        this.statusCode = statusCode
        this.message = message
    }
}
