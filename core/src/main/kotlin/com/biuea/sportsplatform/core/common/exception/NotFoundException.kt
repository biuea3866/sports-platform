package com.biuea.sportsplatform.core.common.exception

import com.biuea.sportsplatform.core.common.exception.BaseException
import org.springframework.http.HttpStatus

class NotFoundException : BaseException {
    constructor() : super(HttpStatus.NOT_FOUND)
    constructor(message: String) : super(HttpStatus.NOT_FOUND, message)
}
