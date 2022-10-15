package com.sistema.votacao.controller

import com.sistema.votacao.exception.BusinessException
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(BusinessException::class)
    fun onException(exception: Exception): ApiError {
        logger.error("error=general message=${exception.message}", exception)
        return ApiError("001", exception.message ?: "")
    }

    //    @ResponseStatus(NOT_ACCEPTABLE) [2]
//    @ExceptionHandler(ApiVersionNotAcceptableException::class)
//    fun onApiVersionNotAcceptableException(exception: ApiVersionNotAcceptableException): ApiError {
//        log.error("error=api version message=${exception.message}", exception)
//
//        return ApiError("VERSION_NOT_ACCEPTABLE", exception.message ?: "")
//    }
//
//    @ResponseStatus(BAD_REQUEST) [3]
//    @ExceptionHandler(MethodArgumentNotValidException::class)
//    fun onMethodArgumentNotValidException(exception: MethodArgumentNotValidException): ApiError {
//        log.error("error=validation message=${exception.message}", exception)
//
//        val errors: Map<String, String> = exception
//            .bindingResult
//            .allErrors
//            .associateBy({ (it as FieldError).field }, { it.defaultMessage ?: "" })
//
//        return ApiError("REQUEST_VALIDATION_ERROR", "Some fields are invalid", errors)
//    }
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}
//todo: finalizar
data class ApiError(
    val code: String,
    val message: String,
)