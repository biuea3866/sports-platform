package com.biuea.sportsplatform.core.common.exception

import com.biuea.sportsplatform.core.common.exception.BaseException
import org.springframework.http.HttpStatus

class NotAcceptableException : BaseException {
    constructor() : super(HttpStatus.NOT_ACCEPTABLE)
    constructor(message: String) : super(HttpStatus.NOT_ACCEPTABLE, message)
}
