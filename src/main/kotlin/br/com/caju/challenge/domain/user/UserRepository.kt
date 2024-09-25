package br.com.caju.challenge.domain.user

import br.com.caju.challenge.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.security.core.userdetails.UserDetails

interface UserRepository: JpaRepository<User, String> {
    fun findByEmail(email: String): User?
}