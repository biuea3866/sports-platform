package com.biuea.sportsplatform.core.common.exception

import com.biuea.sportsplatform.core.common.exception.BaseException
import org.springframework.http.HttpStatus

class UnauthorizedException : BaseException {
    constructor() : super(HttpStatus.UNAUTHORIZED)
    constructor(message: String?) : super(HttpStatus.UNAUTHORIZED, message)
}
