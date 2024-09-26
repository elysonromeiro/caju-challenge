package br.com.caju.challenge.domain.user.usecases

import br.com.caju.challenge.domain.user.User
import br.com.caju.challenge.domain.user.UserRepository
import br.com.caju.challenge.util.GetAuthUser
import jakarta.persistence.EntityNotFoundException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mockStatic
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith



class MeUseCaseTest {

    private lateinit var userRepository: UserRepository
    private lateinit var authentication: Authentication
    private lateinit var meUseCase: MeUseCase
    private lateinit var securityContext: SecurityContext

    @BeforeEach
    fun setUp() {
        userRepository = mock()
        securityContext = mock()
        authentication = mock()
        meUseCase = MeUseCase(userRepository)

        SecurityContextHolder.setContext(securityContext)
        whenever(securityContext.authentication).thenReturn(authentication)
    }

    @Test
    fun `should return authenticated user when user exists in repository`() {
        val authenticatedUser = User("id", "name", "email", "pass")
        val userFromDatabase = User("id", "name", "email", "pass")

        whenever(authentication.principal).thenReturn(authenticatedUser)

        whenever(userRepository.findByEmail(authenticatedUser.email)).thenReturn(userFromDatabase)

        val result = meUseCase.call(Any())

        assertEquals(userFromDatabase, result)
    }

    @Test
    fun `should throw EntityNotFoundException when user is not found in repository`() {
        val authenticatedUser = User("id", "name", "email", "pass")
        whenever(authentication.principal).thenReturn(authenticatedUser)
        whenever(userRepository.findByEmail(authenticatedUser.email)).thenReturn(null)

        assertFailsWith<EntityNotFoundException> {
            meUseCase.call(Any())
        }
    }
}
