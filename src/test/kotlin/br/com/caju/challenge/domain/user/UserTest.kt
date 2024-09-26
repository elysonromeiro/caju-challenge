package br.com.caju.challenge.domain.user

import br.com.caju.challenge.util.CentsToReal
import org.junit.jupiter.api.Test
import kotlin.test.assertIs

class UserTest {
    @Test
    fun `User instance should be defined `() {
        assertIs<User>(User("id", "name", "email", "pass"))
    }

    @Test
    fun `Empty User instance should be defined `() {
        assertIs<User>(User())
    }

}