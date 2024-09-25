package br.com.caju.challenge.controller.auth

import br.com.caju.challenge.controller.ControllerResponse
import br.com.caju.challenge.domain.auth.dtos.Login
import br.com.caju.challenge.domain.auth.TokenService
import br.com.caju.challenge.domain.auth.dtos.UserToAuth
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/auth")
class AuthController(val authManager: AuthenticationManager, val tokenService: TokenService) {

    @PostMapping("/user")
    fun authUser(@RequestBody @Validated userToAuth: UserToAuth): ResponseEntity<ControllerResponse<Login>> {
        val token = UsernamePasswordAuthenticationToken(userToAuth.email, userToAuth.password)
        this.authManager.authenticate(token)

        return ResponseEntity.status(HttpStatus.OK).body(
            ControllerResponse(
                result = Login(
                    tokenService.generate(userToAuth.email)
                ), success = true
            )
        )
    }
}