package br.com.caju.challenge.domain.balance.exceptions

import br.com.caju.challenge.infra.exceptions.BusinessException
import org.springframework.http.HttpStatus

class InsufficientBalanceException() :
    BusinessException(httpStatus = HttpStatus.BAD_REQUEST, errorMessage = "insufficient balance")