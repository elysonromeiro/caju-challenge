package br.com.caju.challenge.domain.auth

import br.com.caju.challenge.domain.user.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AuthService(val userRepository: UserRepository): UserDetailsService {


    override fun loadUserByUsername(username: String): UserDetails? {
        return userRepository.findByEmail(username);
    }
}