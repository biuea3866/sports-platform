package com.biuea.sportsplatform.core.common.advice

import com.biuea.sportsplatform.core.common.exception.BaseException
import com.biuea.sportsplatform.core.common.response.CommonResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.ConversionNotSupportedException
import org.springframework.beans.TypeMismatchException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.http.converter.HttpMessageNotWritableException
import org.springframework.web.HttpMediaTypeNotAcceptableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingPathVariableException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.ServletRequestBindingException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.multipart.support.MissingServletRequestPartException
import org.springframework.web.servlet.NoHandlerFoundException
import java.net.BindException
import javax.validation.ConstraintViolationException
import javax.validation.ValidationException

@ControllerAdvice
class CommonAdvice {
    private val logger = LoggerFactory.getLogger(javaClass)

    @ResponseBody
    @ExceptionHandler(value = [Exception::class])
    fun onException(e: Exception): ResponseEntity<CommonResponse<*>> {
        logger.error(
            "Internal Server Error : ${e.message}, Exception class : ${e.javaClass}",
            e
        )

        return ResponseEntity<CommonResponse<*>>(
            CommonResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR),
            null,
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    @ResponseBody
    @ExceptionHandler(value = [BaseException::class])
    fun onBaseException(e: BaseException): ResponseEntity<CommonResponse<*>> {
        logger.warn(
            "Defined Exception : ${e.javaClass}, Message : ${e.message}",
            e
        )

        return ResponseEntity<CommonResponse<*>>(
            CommonResponse.fail(
                e.message,
                e.statusCode
            ), null, e.statusCode
        )
    }

    @ResponseBody
    @ExceptionHandler(
        value = [
            org.springframework.validation.BindException::class,
        ]
    )
    fun onBindException(e: org.springframework.validation.BindException): ResponseEntity<CommonResponse<*>> {
        return ResponseEntity<CommonResponse<*>>(
            CommonResponse.fail(e.message, HttpStatus.BAD_REQUEST),
            null,
            HttpStatus.BAD_REQUEST,
        )
    }

    @ResponseBody
    @ExceptionHandler(
        value = [
            ConstraintViolationException::class,
            IllegalArgumentException::class
        ]
    )
    fun onValidationException(e: Exception): ResponseEntity<CommonResponse<*>> {
        return ResponseEntity<CommonResponse<*>>(
            CommonResponse.fail(e.message, HttpStatus.BAD_REQUEST),
            null,
            HttpStatus.BAD_REQUEST,
        )
    }

    @ResponseBody
    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun onMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<CommonResponse<*>> {
        val message = e.bindingResult
            .allErrors
            .stream()
            .findFirst()
            .get()
            .defaultMessage

        return ResponseEntity<CommonResponse<*>>(
            CommonResponse.fail(
                message = message,
                code = HttpStatus.BAD_REQUEST
            ),
            null,
            HttpStatus.BAD_REQUEST,
        )
    }

    @ResponseBody
    @ExceptionHandler(
        value = [
            MethodArgumentTypeMismatchException::class,
            HttpRequestMethodNotSupportedException::class,
            HttpMediaTypeNotSupportedException::class,
            HttpMediaTypeNotAcceptableException::class,
            MissingPathVariableException::class,
            MissingServletRequestParameterException::class,
            ServletRequestBindingException::class,
            ConversionNotSupportedException::class,
            TypeMismatchException::class,
            HttpMessageNotReadableException::class,
            HttpMessageNotWritableException::class,
            MissingServletRequestPartException::class,
            BindException::class,
            NoHandlerFoundException::class,
            ValidationException::class
        ]
    )
    fun onHttpDefaultException(e: Exception): ResponseEntity<CommonResponse<*>> {
        logger.warn("Argument Type Mismatch Error : ${e.message}", e)

        return ResponseEntity<CommonResponse<*>>(
            CommonResponse.fail(e.message, HttpStatus.BAD_REQUEST),
            null,
            HttpStatus.BAD_REQUEST
        )
    }
}