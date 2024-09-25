package br.com.caju.challenge.domain.balance.dtos

import br.com.caju.challenge.constants.enums.BalanceKindEnum
import br.com.caju.challenge.constants.enums.TransactionCodeEnum
import br.com.caju.challenge.domain.balance.Balance
import br.com.caju.challenge.domain.transactions.Mcc
import jakarta.validation.constraints.*
import jakarta.persistence.*

data class DebitMakedDto(
    @field:Positive val amountBefore: Int,
    @field:Positive val amountAfter: Int,
    val balance: Balance,
    val mcc: Mcc?,
    val code: TransactionCodeEnum
)