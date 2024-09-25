package br.com.caju.challenge.domain.balance.dtos

import br.com.caju.challenge.constants.enums.BalanceKindEnum
import jakarta.validation.constraints.*
import jakarta.persistence.*

data class CreditToAddDto(
    @Enumerated(EnumType.STRING) val kind: BalanceKindEnum,
    @field:Positive val amountToAdd: Int
)