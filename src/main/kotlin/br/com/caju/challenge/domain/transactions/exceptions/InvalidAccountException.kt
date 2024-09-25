package br.com.caju.challenge.domain.transactions.exceptions

import br.com.caju.challenge.constants.enums.BalanceKindEnum
import br.com.caju.challenge.infra.exceptions.BusinessException
import org.springframework.http.HttpStatus

class InvalidAccountException() : BusinessException(httpStatus = HttpStatus.BAD_REQUEST, errorMessage = "invalid account") {}