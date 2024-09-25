package br.com.caju.challenge.infra.filters

import br.com.caju.challenge.domain.auth.TokenService
import br.com.caju.challenge.domain.user.UserRepository
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class SecurityFilter(val tokenService: TokenService, val userRepository: UserRepository): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token: String? = bearerToken(request);
        if(token != null) {
            val subject = tokenService.getSubject(token);
            val user = userRepository.findByEmail(subject);

            if(user == null) {
                throw RuntimeException("invalid token") as Throwable;
            }

            val auth = UsernamePasswordAuthenticationToken(user, null, user.authorities)
            SecurityContextHolder.getContext().authentication = auth;
        }

        filterChain.doFilter(request, response);
    }

    private fun bearerToken(request: HttpServletRequest): String? {
        val token: String? = request.getHeader("Authorization");

        if(token != null) {
            return token.replace("Bearer ", "");
        }

        return null;
    }


}