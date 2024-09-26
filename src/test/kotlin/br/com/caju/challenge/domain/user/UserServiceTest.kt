package br.com.caju.challenge.domain.user

import br.com.caju.challenge.domain.user.dtos.UserToCreateDto
import br.com.caju.challenge.domain.user.usecases.CreateUserUseCase
import br.com.caju.challenge.domain.user.usecases.MeUseCase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class UserServiceTest {

    private lateinit var userService: UserService
    private lateinit var createUserUseCase: CreateUserUseCase
    private lateinit var meUseCase: MeUseCase

    @BeforeEach
    fun setUp() {
        createUserUseCase = mock()
        meUseCase = mock()
        userService = UserService(createUserUseCase, meUseCase)
    }

    @Test
    fun `should create a user using createUserUseCase`() {
        val userToCreateDto = UserToCreateDto("name", "email@email.com", "pass")
        val createdUser = User("id", "name", "email", "pass")

        whenever(createUserUseCase.execute(userToCreateDto)).thenReturn(createdUser)

        val result = userService.create(userToCreateDto)

        assertEquals(createdUser, result)

        verify(createUserUseCase).execute(userToCreateDto)
    }

    @Test
    fun `should return the current user using meUseCase`() {
        val currentUser = User("id", "name", "email", "pass")

        whenever(meUseCase.execute("")).thenReturn(currentUser)

        val result = userService.me()

        assertEquals(currentUser, result)

        verify(meUseCase).execute("")
    }
}