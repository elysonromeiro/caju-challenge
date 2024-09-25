package br.com.caju.challenge.domain.user.dtos

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size


data class UserToCreateDto(
    @field:NotBlank(message = "name cannot be empty") val name: String,
    
    @field:Email(message = "invalid email") val email: String,

    @field:NotBlank(message = "pass cannot be empty")
    @field:Size(
        min = 6,
        message = "pass must be size > 6"
    )
    var pass: String
)