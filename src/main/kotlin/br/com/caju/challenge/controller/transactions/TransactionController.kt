package br.com.caju.challenge.controller.transactions

import br.com.caju.challenge.controller.ControllerResponse
import br.com.caju.challenge.domain.auth.dtos.UserToAuth
import br.com.caju.challenge.domain.balance.Balance
import br.com.caju.challenge.domain.transactions.Transaction
import br.com.caju.challenge.domain.transactions.TransactionService
import br.com.caju.challenge.domain.transactions.dtos.TransactionCreatedDto
import br.com.caju.challenge.domain.transactions.dtos.TransactionToCreateDto
import br.com.caju.challenge.domain.user.User
import br.com.caju.challenge.domain.user.UserService
import br.com.caju.challenge.domain.user.dtos.UserToCreateDto
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/transactions")
@Validated
class TransactionController(val service: TransactionService) {
    @PostMapping
    @ResponseBody
    fun create(@RequestBody @Validated transactionToCreateDto: TransactionToCreateDto): ResponseEntity<ControllerResponse<TransactionCreatedDto>> {
        val transactionCreated: TransactionCreatedDto = service.create(transactionToCreateDto)
        val response: ControllerResponse<TransactionCreatedDto> = ControllerResponse(success = transactionCreated.success, result = transactionCreated)
        return ResponseEntity.ok(response)
    }
}