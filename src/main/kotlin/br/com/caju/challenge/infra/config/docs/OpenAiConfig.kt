package br.com.caju.challenge.infra.config.docs

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info


@Configuration
class OpenApiConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Caju Challenge")
                    .version("v1.0")
                    .description("OpenAi Docs para api Caju Challenge")
            )
    }
}