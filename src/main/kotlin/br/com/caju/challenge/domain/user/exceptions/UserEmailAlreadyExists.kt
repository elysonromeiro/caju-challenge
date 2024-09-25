package br.com.caju.challenge.domain.user.exceptions

import br.com.caju.challenge.infra.exceptions.BusinessException
import org.springframework.http.HttpStatus

class UserEmailAlreadyExists(email: String) : BusinessException(httpStatus = HttpStatus.PRECONDITION_FAILED, errorMessage = "email $email already exists") {}