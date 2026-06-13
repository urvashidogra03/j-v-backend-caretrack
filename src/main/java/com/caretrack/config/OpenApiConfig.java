package com.caretrack.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;

/**
 * OpenAPI configuration enabling JWT bearer authentication in Swagger UI.
 * Use the "Authorize" button and paste: Bearer <token> after logging in.
 */
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
) //this SecurityScheme Defines the type of security used by the entire API (JWT Bearer Tokens), it define JWT Bearer authentication.Adds "Authorize" button in Swagger
//openapidefinition describes your entire API documentation.Swagger will display this in the header.//API metadata
@OpenAPIDefinition(
        info = @Info(
                title = "CareTrack API",//Name shown in Swagger UI
                version = "0.1.0",      //Your API version (helpful for tracking releases)
                description = "Medical Assistance CRM & Case Management Backend APIs", //Short summary of your application
                contact = @Contact(name = "CareTrack Backend Team", email = "backend@caretrack.health")
        ),
        security = {
                @SecurityRequirement(name = "bearerAuth") //“Use the bearerAuth scheme for all API requests.”every request have jwt unless marked public
        },
        servers = {
                @Server(url = "http://localhost:8080")   //Defines the base URL for all API calls inside Swagger.
        }
)
public class OpenApiConfig {

}
//It is an empty class — all configuration is done via annotations.Spring Boot automatically picks it up because of the annotations.