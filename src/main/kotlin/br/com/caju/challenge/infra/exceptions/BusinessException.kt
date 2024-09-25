package br.com.caju.challenge.infra.exceptions

import org.springframework.http.HttpStatus

open class BusinessException(val httpStatus: HttpStatus, val errorMessage: String) : RuntimeException(errorMessage) {}