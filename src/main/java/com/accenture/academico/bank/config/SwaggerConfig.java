package com.accenture.academico.bank.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    info = @Info(
        contact = @Contact(
            name = "André, Ângela, Catarina"            
        ),
        description = "<p>API Sistema Bancário BANK</p><p>Projeto de conclusão de curso de Java Avançado</p>",
        title = "API Sistema Bancário BANK",
        version = "1.0",
        license = @License(
            name = "Licence Accenture",
            url = "https://accenture.com"
        )
        // ,termsOfService = "Termos"        
    ),
    servers = {
        @Server(
            description = "Local ENV",
            url = "http://localhost:8080"
        )
    }
    // @SecurityScheme(
    //     name = "bearerAuth",
    //     description = "JWT auth description",
    //     scheme = "bearer",
    //     type = SecuritySchemeType.HTTP,
    //     bearerFormat = "JWT",
    //     in = SecuritySchemeIn.HEADER
    // )
)
public class SwaggerConfig {
    
}
