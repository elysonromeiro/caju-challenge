package br.com.caju.challenge.domain.user

import br.com.caju.challenge.domain.user.usecases.CreateUserUseCase
import br.com.caju.challenge.domain.user.dtos.UserToCreateDto
import br.com.caju.challenge.domain.user.usecases.MeUseCase
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(val createUserUseCase: CreateUserUseCase, val meUseCase: MeUseCase) {
    fun create(userToCreateDto: UserToCreateDto): User {
        return createUserUseCase.execute(userToCreateDto)
    }


    fun me(): User = meUseCase.execute("")

    fun <T : Any> Optional<out T>.toList(): List<T> =
        if (isPresent) listOf(get()) else emptyList()
}