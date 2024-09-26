package br.com.caju.challenge.infra.config.security

import br.com.caju.challenge.infra.filters.SecurityFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(val securityFilter: SecurityFilter) {

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .sessionManagement { sessionManagement -> SessionCreationPolicy.STATELESS }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers(HttpMethod.GET, "/swagger-ui.html")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, "/v1/users", "/v1/auth/user")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, "/v1/auth/user")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, "/v1/transactions")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            }
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}