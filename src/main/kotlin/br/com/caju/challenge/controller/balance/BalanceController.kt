package br.com.caju.challenge.controller.balance

import br.com.caju.challenge.controller.ControllerResponse
import br.com.caju.challenge.domain.balance.Balance
import br.com.caju.challenge.domain.balance.BalanceService
import br.com.caju.challenge.domain.balance.dtos.CreditToAddDto
import br.com.caju.challenge.domain.user.User
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/balances")
@Validated
class BalanceController(val service: BalanceService) {
    @Operation(summary = "Adiciona créditos para o usuário", description = "retorna o saldo atualizado")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "crédito adicionado com sucesso"),
        ]
    )
    @PostMapping("add-credits")
    fun addCredits(@Valid @RequestBody creditToAddDto: CreditToAddDto): ResponseEntity<Balance> {
        return ResponseEntity.ok<Balance>(service.addCredits(creditToAddDto))
    }
}