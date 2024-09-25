package br.com.caju.challenge.domain.user.usecases

import br.com.caju.challenge.domain.ExtendedUseCase
import br.com.caju.challenge.domain.balance.Balance
import br.com.caju.challenge.domain.balance.usecases.CreateUserBalancesUseCase
import br.com.caju.challenge.domain.user.User
import br.com.caju.challenge.domain.user.UserRepository
import br.com.caju.challenge.domain.user.dtos.UserToCreateDto
import br.com.caju.challenge.domain.user.exceptions.UserEmailAlreadyExists
import br.com.caju.challenge.infra.interfaces.IUseCase
import br.com.caju.challenge.util.PasswordUtil
import org.springframework.stereotype.Service

@Service
class CreateUserUseCase(val userRepository: UserRepository, val createUserBalancesUseCase: CreateUserBalancesUseCase) :
    IUseCase<UserToCreateDto, User>, ExtendedUseCase<UserToCreateDto, User>() {
    override fun call(params: UserToCreateDto): User {
        logBegin(params)

        val pass = PasswordUtil.Companion.encode(params.pass)

        val emailAlreadyExists: User? = userRepository.findByEmail(params.email);

        if(emailAlreadyExists != null) {
            throw UserEmailAlreadyExists(params.email);
        }

        val user: User =
            userRepository.save(User(id = null, name = params.name, pass = pass, email = params.email))

        val balances: ArrayList<Balance> = createUserBalancesUseCase.execute(user)
        user.setBalancesList(balances)

        logResult(mapOf("newUserId" to user.id))

        return user
    }
}