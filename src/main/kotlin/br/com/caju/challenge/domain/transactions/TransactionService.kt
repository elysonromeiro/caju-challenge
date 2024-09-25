package br.com.caju.challenge.domain.transactions

import br.com.caju.challenge.domain.transactions.dtos.TransactionCreatedDto
import br.com.caju.challenge.domain.transactions.dtos.TransactionToCreateDto
import br.com.caju.challenge.domain.transactions.usecases.CreateTransactionUseCase
import org.springframework.stereotype.Service

@Service
class TransactionService(val createTransactionUseCase: CreateTransactionUseCase) {
    fun create(transactionToCreateDto: TransactionToCreateDto): TransactionCreatedDto {
        return createTransactionUseCase.execute(transactionToCreateDto)
    }
}