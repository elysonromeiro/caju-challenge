package br.com.caju.challenge.domain.balance.usecases

import br.com.caju.challenge.constants.enums.BalanceKindEnum
import br.com.caju.challenge.domain.ExtendedUseCase
import br.com.caju.challenge.domain.balance.Balance
import br.com.caju.challenge.domain.balance.BalanceRepository
import br.com.caju.challenge.domain.user.User
import br.com.caju.challenge.infra.interfaces.IUseCase
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CreateUserBalancesUseCase(val balanceRepository: BalanceRepository) : IUseCase<User, ArrayList<Balance>>, ExtendedUseCase<User, ArrayList<Balance>>() {
    override fun call(params: User): ArrayList<Balance> {
        logBegin(params);

        val balancesToAddOnUser: ArrayList<Balance> = ArrayList<Balance>()

        val foodBalance = Balance(
            id = UUID.randomUUID().toString(),
            name = BalanceKindEnum.FOOD.toString(),
            amount = 0,
            kind = BalanceKindEnum.FOOD,
            user = params
        )

        val mealBalance = Balance(
            id = UUID.randomUUID().toString(),
            name = BalanceKindEnum.MEAL.toString(),
            amount = 0,
            kind = BalanceKindEnum.MEAL,
            user = params
        )
        val cashBalance = Balance(
            id = UUID.randomUUID().toString(),
            name = BalanceKindEnum.CASH.toString(),
            amount = 0,
            kind = BalanceKindEnum.CASH,
            user = params
        )

        balancesToAddOnUser.add(foodBalance)
        balancesToAddOnUser.add(mealBalance)
        balancesToAddOnUser.add(cashBalance)

        balancesToAddOnUser.forEach { balance ->
            balanceRepository.save(balance)
        }

        logResult(
            mapOf(
                "userId" to params.id,
                "newFoodBalanceId" to foodBalance.id,
                "mealBalance" to mealBalance.id,
                "cashBalance" to cashBalance.id
            )
        )

        return balancesToAddOnUser
    }
}