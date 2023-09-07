package com.biuea.sportsplatform.core.common.response

import org.springframework.http.HttpStatus

data class CommonResponse<T>(
    val success: Boolean,
    val data: T?,
    val message: String?,
    val code: HttpStatus?
) {
    companion object {
        fun <T> success(data: T?, message: String?): CommonResponse<T> {
            return CommonResponse<T>(
                success = true,
                data = data,
                message = message,
                code = null,
            )
        }

        fun success(message: String): CommonResponse<Unit> {
            return success(null, message)
        }

        fun <T> success(data: T?): CommonResponse<T> {
            return success(data, null)
        }

        fun fail(message: String?, code: HttpStatus?): CommonResponse<*> {
            return CommonResponse(
                success = false,
                data = null,
                message = message,
                code = code,
            )
        }

        fun fail(code: HttpStatus?): CommonResponse<*> {
            return fail(null, code)
        }
    }
}
