package br.com.caju.challenge.domain.balance

import br.com.caju.challenge.domain.balance.dtos.CreditToAddDto
import br.com.caju.challenge.domain.balance.usecases.AddCreditUseCase
import org.springframework.stereotype.Service

@Service
class BalanceService(val addCreditUseCase: AddCreditUseCase) {
    fun addCredits(creditToAddDto: CreditToAddDto): Balance {
        return addCreditUseCase.execute(creditToAddDto)
    }
}