package br.com.caju.challenge.domain.user

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import java.util.*

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private lateinit var userRepository: UserRepository

    private lateinit var user: User

    @BeforeEach
    fun setUp() {
        user = User(
            id = UUID.randomUUID().toString(),
            name = "name",
            email = "test@example.com",
            pass = "password123"
        )

        userRepository.save(user)
    }

    @Test
    fun `should find user by email`() {
        val result = userRepository.findByEmail("test@example.com")

        assertNotNull(result)
        assertEquals(user.email, result?.email)
    }

    @Test
    fun `should return null when user not found by email`() {
        val result = userRepository.findByEmail("nonexistent@example.com")

        assertNull(result)
    }
}