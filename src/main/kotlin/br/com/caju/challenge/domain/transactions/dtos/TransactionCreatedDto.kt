package br.com.caju.challenge.domain.transactions.dtos

data class TransactionCreatedDto(
    val id: String? = null,
    val success: Boolean,
    val code: String
)