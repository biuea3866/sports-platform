package com.biuea.sportsplatform.core.common.exception

import org.springframework.http.HttpStatus

class BadRequestException : BaseException {
    constructor() : super(HttpStatus.BAD_REQUEST)
    constructor(message: String?) : super(HttpStatus.BAD_REQUEST, message)
}
