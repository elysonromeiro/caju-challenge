package br.com.caju.challenge.domain.balance.usecases

import br.com.caju.challenge.constants.enums.BalanceKindEnum
import br.com.caju.challenge.constants.enums.TransactionCodeEnum
import br.com.caju.challenge.domain.ExtendedUseCase
import br.com.caju.challenge.domain.balance.Balance
import br.com.caju.challenge.domain.balance.BalanceRepository
import br.com.caju.challenge.domain.balance.dtos.DebitMakedDto
import br.com.caju.challenge.domain.balance.dtos.MakeDebitDto
import br.com.caju.challenge.domain.balance.exceptions.BalanceNotFound
import br.com.caju.challenge.domain.balance.exceptions.InsufficientBalanceException
import br.com.caju.challenge.domain.transactions.Mcc
import br.com.caju.challenge.domain.transactions.MccRepository
import br.com.caju.challenge.domain.user.User
import br.com.caju.challenge.infra.interfaces.IUseCase
import br.com.caju.challenge.util.CentsToReal
import br.com.caju.challenge.util.GetAuthUser
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class MakeDebitUseCase(val balanceRepository: BalanceRepository, val mccRepository: MccRepository) :
    IUseCase<MakeDebitDto, DebitMakedDto>, ExtendedUseCase<MakeDebitDto, DebitMakedDto>() {
    override fun call(params: MakeDebitDto): DebitMakedDto {
        try {
            logBegin(params)

            val totalAmount: Int = CentsToReal.convert(params.totalAmount)
            val mcc = getMcc(params.mcc);
            val balance: Balance = identifyBalanceToDebit(params.user, mcc)
            val balanceToUse = checkBalanceToUse(params.user, balance, totalAmount)

            val balanceBefore = balanceToUse.amount

            balanceToUse.amount = balanceToUse.amount - totalAmount
            val balanceUpdated = balanceRepository.save<Balance>(balanceToUse)
            val balanceAfter = balanceUpdated.amount


            logResult(
                mapOf(
                    "balanceUpdatedId" to balanceUpdated.id,
                    "newAmount" to balanceUpdated.id,
                    "balanceBefore" to balanceBefore,
                    "balanceAfer" to balanceAfter,
                )
            )
            return DebitMakedDto(amountBefore = balanceBefore, amountAfter = balanceAfter, balanceUpdated, mcc = mcc, code = TransactionCodeEnum.CODE_00)
        } catch (e: BalanceNotFound) {
            return DebitMakedDto(amountBefore = 0, amountAfter = 0, Balance(), mcc = null, code = TransactionCodeEnum.CODE_07)
        } catch (e: InsufficientBalanceException) {
            return DebitMakedDto(amountBefore = 0, amountAfter = 0, Balance(), mcc = null, code = TransactionCodeEnum.CODE_51)
        } catch (e: Exception) {
            return DebitMakedDto(amountBefore = 0, amountAfter = 0, Balance(), mcc = null, code = TransactionCodeEnum.CODE_07)
        }
    }

    private fun getMcc(mcc: String): Mcc? {
        return mccRepository.findByMcc(mcc).getOrNull()

    };

    private fun identifyBalanceToDebit(user: User, mcc: Mcc?): Balance {

        var kind: BalanceKindEnum = BalanceKindEnum.CASH

        if (mcc != null) {
            kind = mcc.balanceKind
        }

        val balance: Balance? =
            balanceRepository.findByUserIdAndKind(userId = user.id.toString(), kind = kind).getOrNull()

        if (balance == null) {
            throw BalanceNotFound(kind = kind, userId = user.id.toString())
        }

        return balance
    }

    private fun getBalanceCash(user: User): Balance {


        val balance: Balance? =
            balanceRepository.findByUserIdAndKind(userId = user.id.toString(), kind = BalanceKindEnum.CASH)
                .getOrNull()

        if (balance == null) {
            throw BalanceNotFound(kind = BalanceKindEnum.CASH, userId = user.id.toString())
        }

        return balance
    }

    private fun checkBalanceToUse(user: User, balance: Balance, totalAmount: Int): Balance {
        println(totalAmount)
        println(balance.amount)
        if (balance.amount >= totalAmount) {
            return balance
        }

        val cash = getBalanceCash(user)
        println(cash.amount)
        if (cash.amount >= totalAmount) {
            return cash
        }

        throw InsufficientBalanceException()
    }
}