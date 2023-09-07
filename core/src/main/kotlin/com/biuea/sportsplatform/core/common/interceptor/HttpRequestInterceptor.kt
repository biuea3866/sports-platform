package com.biuea.sportsplatform.core.common.interceptor

import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class HttpRequestInterceptor: HandlerInterceptor {
    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        val token = request.getHeader("X-User-Id")

        return when (token == null) {
            true -> {
                response.status = HttpServletResponse.SC_UNAUTHORIZED
                return false
            }
            false -> true
        }
    }
}