package br.com.caju.challenge.domain.user.usecases

import br.com.caju.challenge.domain.ExtendedUseCase
import br.com.caju.challenge.domain.user.User
import br.com.caju.challenge.domain.user.UserRepository
import br.com.caju.challenge.infra.interfaces.IUseCase
import br.com.caju.challenge.util.GetAuthUser
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class MeUseCase(val userRepository: UserRepository) : IUseCase<Any, User>, ExtendedUseCase<Any, User>() {
    override fun call(params: Any): User {
        logBegin(params)

        val userAuthenticated = GetAuthUser.user()

        val user: User? = userRepository.findByEmail(userAuthenticated.email)

        if (user == null) {
            throw EntityNotFoundException()
        }

        logResult(user)

        return user
    }
}