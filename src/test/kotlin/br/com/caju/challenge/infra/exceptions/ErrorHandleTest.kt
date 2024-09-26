package br.com.caju.challenge.infra.exceptions


import br.com.caju.challenge.infra.exceptions.ErrorHandler.ErrorValidation
import org.mockito.Mockito
import org.springframework.core.MethodParameter
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.MethodArgumentNotValidException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs


class ErrorHandlerTest {
    var errorHandler: ErrorHandler = ErrorHandler()

    @Test
    fun `ErrorValidation instance should be defined `() {
        assertIs<ErrorValidation>(ErrorValidation("field", "message"))
    }

    @Test
    fun `ErrorValidation instance should be defined without message`() {
        assertIs<ErrorValidation>(ErrorValidation("field", null))
    }

    @Test
    fun `should return 404 when EntityNotFoundException is thrown`() {
        assertEquals(errorHandler.handleError404(), ResponseEntity.notFound().build())
    }

    @Test
    fun `should return 400 when MethodArgumentNotValidException is thrown`() {
        val mockError = MethodArgumentNotValidException(Mockito.mock(MethodParameter::class.java), Mockito.mock(
            BindingResult::class.java))
        val errors = mockError.bindingResult.fieldErrors
        assertEquals(
            errorHandler.handleError400(mockError),
            ResponseEntity.badRequest().body(errors.map { ErrorValidation(it) })
        )

    }

    @Test
    fun `should return 400 when HttpMessageNotReadableException is thrown`() {
        assertEquals(errorHandler.handleError404(), ResponseEntity.notFound().build())

    }

    @Test
    fun `should return 401 when BadCredentialsException is thrown`() {
        assertEquals(
            errorHandler.handleErrorBadCredentials(),
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid credentials")
        )
    }

    @Test
    fun `should return 401 when AuthenticationException is thrown`() {
        assertEquals(
            errorHandler.handleErrorAuthentication(),
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("auth failed")
        )

    }

    @Test
    fun `should return 403 when AccessDeniedException is thrown`() {
        assertEquals(errorHandler.handleErrorForbidden(), ResponseEntity.status(HttpStatus.FORBIDDEN).body("forbidden"))

    }

    @Test
    fun `should return 500 when general Exception is thrown`() {
        val mockError = Mockito.mock(Exception::class.java)
        assertEquals(
            errorHandler.handleError500(mockError),
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + mockError.localizedMessage)
        )

    }

    @Test
    fun `should return business error with correct status and message`() {
        val mockError = BusinessException(HttpStatus.BAD_REQUEST, "mockError")
        assertEquals(
            errorHandler.handleBusinessException(mockError),
            ResponseEntity.status(mockError.httpStatus).body(mapOf<String, String>("error" to mockError.errorMessage))
        )

    }
}