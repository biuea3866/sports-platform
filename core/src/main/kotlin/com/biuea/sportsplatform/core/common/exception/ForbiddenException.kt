package com.biuea.sportsplatform.core.common.exception

import com.biuea.sportsplatform.core.common.exception.BaseException
import org.springframework.http.HttpStatus

class ForbiddenException : BaseException {
    constructor() : super(HttpStatus.FORBIDDEN)
    constructor(message: String?) : super(HttpStatus.FORBIDDEN, message)
}
