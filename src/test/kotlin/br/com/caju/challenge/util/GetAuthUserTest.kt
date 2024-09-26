package br.com.caju.challenge.util

import br.com.caju.challenge.domain.user.User
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import kotlin.test.assertEquals
import kotlin.test.assertIs

class GetAuthUserTest {

    private lateinit var securityContext: SecurityContext
    private lateinit var authentication: Authentication
    private lateinit var mockUser: User

    @BeforeEach
    fun setUp() {
        securityContext = mock()
        authentication = mock()
        mockUser = User(null, "name", "email", "pass")

        SecurityContextHolder.setContext(securityContext)
        whenever(securityContext.authentication).thenReturn(authentication)
        whenever(authentication.principal).thenReturn(mockUser)
    }

    @Test
    fun `GetAuthUser instance should be defined `() {
        assertIs<GetAuthUser>(GetAuthUser())
    }


    @Test
    fun `should return the authenticated user from SecurityContextHolder`() {
        val result = GetAuthUser.user()
        assertEquals(mockUser, result)
    }
}