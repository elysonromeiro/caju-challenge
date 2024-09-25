package br.com.caju.challenge.domain.transactions.dtos

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive

data class TransactionToCreateDto(
    @field:NotBlank val account: String,
    @field:Positive val totalAmount: Double,
    @field:NotBlank val mcc: String,
    @field:NotBlank val merchant: String,
)