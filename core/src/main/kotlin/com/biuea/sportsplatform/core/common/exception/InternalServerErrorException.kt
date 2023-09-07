package com.biuea.sportsplatform.core.common.exception

import com.biuea.sportsplatform.core.common.exception.BaseException
import org.springframework.http.HttpStatus

class InternalServerErrorException : BaseException {
    constructor() : super(HttpStatus.INTERNAL_SERVER_ERROR)
    constructor(message: String?) : super(
        HttpStatus.INTERNAL_SERVER_ERROR,
        message
    )
}
