package br.com.caju.challenge.util

import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder

class PasswordUtil {
    companion object {
        fun encode(text: String): String {
            val passwordEncoder: PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
            return passwordEncoder.encode(text)
        }

        fun matches(textToCompare: String, password: String): Boolean {
            val passwordEncoder: PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
            return passwordEncoder.matches(textToCompare, password)
        }
    }
}