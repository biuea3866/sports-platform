package com.biuea.sportsplatform.core.common.exception

import org.springframework.http.HttpStatus

class BadSignatureException(message: String?) :
    BaseException(HttpStatus.BAD_REQUEST, message)
