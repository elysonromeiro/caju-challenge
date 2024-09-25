package br.com.caju.challenge.domain.balance.usecases

import br.com.caju.challenge.domain.ExtendedUseCase
import br.com.caju.challenge.domain.balance.Balance
import br.com.caju.challenge.domain.balance.BalanceRepository
import br.com.caju.challenge.domain.balance.dtos.CreditToAddDto
import br.com.caju.challenge.domain.balance.exceptions.BalanceNotFound
import br.com.caju.challenge.infra.interfaces.IUseCase
import br.com.caju.challenge.util.GetAuthUser
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Service
class AddCreditUseCase(val balanceRepository: BalanceRepository) : IUseCase<CreditToAddDto, Balance>,
    ExtendedUseCase<CreditToAddDto, Balance>() {
    override fun call(params: CreditToAddDto): Balance {
        logBegin(params)

        val authenticatedUser = GetAuthUser.user()

        val balance: Balance? =
            balanceRepository.findByUserIdAndKind(userId = authenticatedUser.id.toString(), kind = params.kind).getOrNull()

        if (balance == null) {
            throw BalanceNotFound(kind = params.kind, userId = authenticatedUser.id.toString())
        }

        balance.amount = balance.amount + params.amountToAdd
        val balanceUpdated = balanceRepository.save<Balance>(balance)


        logResult(
            mapOf(
                "balanceUpdatedId" to balanceUpdated.id,
                "newAmount" to balanceUpdated.id,
            )
        )
        return balanceUpdated
    }
}