package com.caretrack;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * CareTrack Backend
 *
 * Layered architecture:
 * - Controller Layer: REST API endpoints
 * - Service Layer: business logic and workflow validations
 * - Repository Layer: data access via Spring Data JPA
 * - Security: JWT, RBAC
 * - Cross-cutting: Audit, Reporting, Integrations
 *
 * This Spring Boot application is IntelliJ-friendly (Maven project),
 * uses Java 17 and Spring Boot 3.x per pom.xml configuration.
 */









@SpringBootApplication
//OpenAPI is the specification (rules)....Swagger is the tool that implements OpenAPI

@OpenAPIDefinition(
        info = @Info(
                title = "CareTrack API",
                version = "0.1.0",
                description = "Medical Assistance CRM & Case Management Backend APIs",
                contact = @Contact(name = "CareTrack Backend Team", email = "backend@caretrack.health")
        )
)
public class CareTrackApplication {

    public static void main(String[] args) { SpringApplication.run(CareTrackApplication.class, args);
    }
}
