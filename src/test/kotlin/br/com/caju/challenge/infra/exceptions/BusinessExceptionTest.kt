package br.com.caju.challenge.infra.exceptions

import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import kotlin.test.assertIs

class BusinessExceptionTest {

    @Test
    fun `BusinessException instance should be defined `() {
        assertIs<BusinessException>(BusinessException(HttpStatus.OK, "message"))
    }
}