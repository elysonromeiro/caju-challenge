package br.com.caju.challenge.domain.balance.exceptions

import br.com.caju.challenge.constants.enums.BalanceKindEnum
import br.com.caju.challenge.infra.exceptions.BusinessException
import org.springframework.http.HttpStatus

class BalanceNotFound(kind: BalanceKindEnum, userId: String) : BusinessException(httpStatus = HttpStatus.BAD_REQUEST, errorMessage = "balance $kind not found for user $userId") {}