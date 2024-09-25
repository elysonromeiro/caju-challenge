package br.com.caju.challenge.domain.balance.dtos

import br.com.caju.challenge.domain.user.User
import jakarta.validation.constraints.Positive

data class MakeDebitDto(
    @field:Positive var totalAmount: Double, @field:Positive val mcc: String, val user: User
)