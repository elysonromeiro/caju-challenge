package br.com.caju.challenge.util

import br.com.caju.challenge.domain.user.User
import org.springframework.security.core.context.SecurityContextHolder

class GetAuthUser {
    companion object {
        fun user(): User {
            val authentication = SecurityContextHolder.getContext().authentication
            return (authentication.principal) as User
        }
    }
}