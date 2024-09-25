package br.com.caju.challenge.infra.exceptions

import jakarta.persistence.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.access.AccessDeniedException
import org.springframework.validation.FieldError

@RestControllerAdvice
class ErrorHandler {

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleError404(): ResponseEntity<Void> {
        return ResponseEntity.notFound().build()
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleError400(ex: MethodArgumentNotValidException): ResponseEntity<List<ErrorValidation>> {
        val errors = ex.bindingResult.fieldErrors
        return ResponseEntity.badRequest().body(errors.map { ErrorValidation(it) })
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleError400(ex: HttpMessageNotReadableException): ResponseEntity<String?> {
        return ResponseEntity.badRequest().body(ex.message)
    }

    @ExceptionHandler(BadCredentialsException::class)
    fun handleErrorBadCredentials(): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid credentials")
    }

    @ExceptionHandler(AuthenticationException::class)
    fun handleErrorAuthentication(): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("auth failed")
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun handleErrorForbidden(): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("forbidden")
    }

    @ExceptionHandler(Exception::class)
    fun handleError500(ex: Exception): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + ex.localizedMessage)
    }

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(ex: BusinessException): ResponseEntity<Map<String, String>> {
        return ResponseEntity.status(ex.httpStatus).body(mapOf<String, String>("error" to ex.errorMessage))
    }

    data class ErrorValidation(val field: String, val message: String?) {
        constructor(error: FieldError) : this(error.field, error.defaultMessage)
    }
}