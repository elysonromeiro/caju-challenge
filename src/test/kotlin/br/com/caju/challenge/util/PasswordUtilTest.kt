package br.com.caju.challenge.util

import org.junit.jupiter.api.Test
import kotlin.test.assertTrue
import kotlin.test.assertFalse
import kotlin.test.assertIs

class PasswordUtilTest {

    @Test
    fun `PasswordUtil instance should be defined `() {
        assertIs<PasswordUtil>(PasswordUtil())
    }

    @Test
    fun `encode should encode the plain text password`() {
        val pass = "pass"

        val encodedPassword = PasswordUtil.encode(pass)
        assertTrue(encodedPassword != pass)
    }

    @Test
    fun `matches should return true when the password matches the encoded password`() {
        val pass = "pass"

        val encodedPassword = PasswordUtil.encode(pass)
        assertTrue(PasswordUtil.matches(pass, encodedPassword))
    }

    @Test
    fun `matches should return false when the password does not match the encoded password`() {
        val pass = "pass"
        val wrongPassword = "wrongPassword"

        val encodedPassword = PasswordUtil.encode(pass)

        assertFalse(PasswordUtil.matches(wrongPassword, encodedPassword))
    }
}