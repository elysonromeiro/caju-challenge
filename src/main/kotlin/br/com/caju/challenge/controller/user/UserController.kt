package br.com.caju.challenge.controller.user

import br.com.caju.challenge.controller.ControllerResponse
import br.com.caju.challenge.domain.balance.Balance
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
@RequestMapping("/v1/users")
@Validated
class UserController(val service: UserService) {
    @PostMapping
    fun createUser(@Valid @RequestBody userToCreateDto: UserToCreateDto): ResponseEntity<User> {
        return ResponseEntity.ok<User>(service.create(userToCreateDto))
    }

    @GetMapping("/me")
    @ResponseBody
    fun me(): ResponseEntity<ControllerResponse<User>> {
        val response: ControllerResponse<User> = ControllerResponse(success = true, result= service.me())
        return ResponseEntity.ok(response)
    }
}