package com.biuea.sportsplatform.core.common.exception

import com.biuea.sportsplatform.core.common.exception.BaseException
import org.springframework.http.HttpStatus

class WrongTokenPayloadException : BaseException {
    constructor() : super(HttpStatus.BAD_REQUEST, "올바르지 않은 인증 링크 정보입니다.")
}
