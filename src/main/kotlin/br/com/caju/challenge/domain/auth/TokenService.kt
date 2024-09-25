package br.com.caju.challenge.domain.auth

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Date
import javax.crypto.SecretKey

@Service
class TokenService {
    @Value("\${app.envs.jwt.secret}")
    private lateinit var secretKey: String;

    fun generate(username: String): String {
        return Jwts.builder().subject(username).signWith(secret()).expiration(expirationDate()).compact();
    }

    private fun expirationDate(): Date {
        return Date.from(LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")));
    }

    fun getSubject(token: String): String {
        return try {
            Jwts.parser().verifyWith(secret()).build().parseSignedClaims(token).getPayload().subject;
        } catch (e: Exception) {
            throw RuntimeException("invalid token");
        }
    }

    private fun secret(): SecretKey {
        return Keys.hmacShaKeyFor(secretKey.toByteArray());
    }


}