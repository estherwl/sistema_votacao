package com.sistema.votacao.controller

import com.sistema.votacao.exception.BusinessException
import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.RestClientException

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(BusinessException::class)
    fun onBusinessException(exception: Exception): ResponseEntity<String> {
        logger.error("error=general message=${exception.message}", exception)
        return ResponseEntity<String>(exception.message ?: "", HttpStatus.CONFLICT)
    }

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun onDataException(exception: Exception): ResponseEntity<String> {
        logger.error("error=general message=${exception.message}", exception)
        return ResponseEntity<String>(exception.message ?: "", HttpStatus.CONFLICT)
    }

    @ExceptionHandler(NoSuchElementException::class)
    fun onNotFoundException(exception: Exception): ResponseEntity<String> {
        logger.error("error=general message=${exception.message}", exception)
        return ResponseEntity<String>(exception.message ?: "", HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(RestClientException::class)
    fun onServerErrorException(exception: Exception): ResponseEntity<String> {
        logger.error("error=general message=${exception.message}", exception)
        return ResponseEntity<String>(exception.message ?: "", HttpStatus.INTERNAL_SERVER_ERROR)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}
