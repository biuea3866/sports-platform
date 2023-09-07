package com.biuea.sportsplatform.core.common.exception

import org.springframework.http.HttpStatus

class BadGatewayException : BaseException {
    constructor() : super(HttpStatus.BAD_GATEWAY)
    constructor(message: String?) : super(HttpStatus.BAD_GATEWAY, message)
}
