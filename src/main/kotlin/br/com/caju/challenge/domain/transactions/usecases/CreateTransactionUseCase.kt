package br.com.caju.challenge.domain.transactions.usecases

import br.com.caju.challenge.constants.enums.TransactionCodeEnum
import br.com.caju.challenge.domain.ExtendedUseCase
import br.com.caju.challenge.domain.balance.dtos.DebitMakedDto
import br.com.caju.challenge.domain.balance.dtos.MakeDebitDto
import br.com.caju.challenge.domain.balance.usecases.MakeDebitUseCase
import br.com.caju.challenge.domain.transactions.Transaction
import br.com.caju.challenge.domain.transactions.TransactionRepository
import br.com.caju.challenge.domain.transactions.dtos.TransactionCreatedDto
import br.com.caju.challenge.domain.transactions.dtos.TransactionToCreateDto
import br.com.caju.challenge.domain.transactions.exceptions.InvalidAccountException
import br.com.caju.challenge.domain.user.UserRepository
import br.com.caju.challenge.infra.interfaces.IUseCase
import br.com.caju.challenge.util.CentsToReal
import br.com.caju.challenge.util.GetAuthUser
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class CreateTransactionUseCase(
    val transactionRepository: TransactionRepository,
    val userRepository: UserRepository,
    val makeDebitUseCase: MakeDebitUseCase
) : IUseCase<TransactionToCreateDto, TransactionCreatedDto>, ExtendedUseCase<TransactionToCreateDto, TransactionCreatedDto>() {
    override fun call(params: TransactionToCreateDto): TransactionCreatedDto {
        logBegin(params)

        val user = userRepository.findById(params.account).getOrNull()

        if(user == null) {
            throw InvalidAccountException()
        }

        val debitMaked: DebitMakedDto = makeDebitUseCase.execute(MakeDebitDto(totalAmount = params.totalAmount, mcc = params.mcc, user = user))

        if(debitMaked.code != TransactionCodeEnum.CODE_00) {
            return TransactionCreatedDto(success = false, code = debitMaked.code.toString());
        }

        val transaction: Transaction = transactionRepository.save<Transaction>(
            Transaction(
                null,
                params.account,
                CentsToReal.convert(params.totalAmount),
                debitMaked.amountBefore,
                debitMaked.amountAfter,
                debitMaked.balance,
                user,
                debitMaked.mcc
            )
        )

        logResult(
            mapOf(
                "newTransactionId" to transaction.id,
            )
        )
        return TransactionCreatedDto(success = true, code = debitMaked.code.toString(), id = transaction.id);

    }
}