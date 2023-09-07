package com.biuea.sportsplatform.core.common.exception

import com.biuea.sportsplatform.core.common.exception.BaseException
import org.springframework.http.HttpStatus

class UnauthorizedKeyException : BaseException {
    constructor() : super(HttpStatus.UNAUTHORIZED, "유효하지 않은 키입니다.")
}
