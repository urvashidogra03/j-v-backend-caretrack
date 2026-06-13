# CareTrack - Medical Assistance CRM & Case Management Backend

A Spring Boot backend for a medical assistance CRM and case management system, designed for health insurance assistance companies. This project implements the core backend APIs for case management, provider management, communications, calls, invoicing, and reporting.

## Architecture

The backend follows a modular layered Spring Boot architecture designed for scalability and maintainability:

1. **Client Layer** – Web and mobile applications (not included in this repo)
2. **Controller Layer** – REST APIs
3. **Service Layer** – Business logic and validations
4. **Repository Layer** – Database access using JPA/SQL
5. **Supporting Layers** – Security, Integrations, Reporting, Audit

![Architecture Diagram](docs/architecture.png)

## Core Modules

- **Authentication & User Management**: Login, JWT authentication, roles, permissions
- **Case Management**: Case creation, assignment, tracking, SLA, closure
- **Provider Management**: Hospitals/providers with location mapping
- **Communication**: Email & WhatsApp messaging linked to cases
- **VoIP Calling**: Call initiation, logs, recording metadata
- **Invoicing**: Invoices, settlements, cost analysis
- **Reporting & MIS**: Dashboards and operational reports
- **Audit & Compliance**: Traceability and compliance logs

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- IntelliJ IDEA (recommended)

### Running the Application

1. Clone the repository
2. Open in IntelliJ IDEA as a Maven project
3. Run the `CareTrackApplication` main class

The application will start on port 8080 with:
- H2 in-memory database (file-based, persisted to `./data/caretrack-db`)
- Swagger UI at http://localhost:8080/swagger-ui
- H2 Console at http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:file:./data/caretrack-db`, username: `sa`, no password)

### Default Admin User

- Email: `admin@caretrack.health`
- Password: `Admin@123`

## API Documentation

The API is documented using OpenAPI/Swagger. After starting the application, visit:
- http://localhost:8080/swagger-ui

### Authentication

1. Use `POST /api/auth/login` with the admin credentials to get a JWT token
2. Click the "Authorize" button in Swagger UI and enter: `Bearer <your_token>`
3. All other endpoints are now accessible based on your role permissions

### Key Endpoints

- **Authentication**: `POST /api/auth/login`
- **Cases**: 
  - `POST /api/cases`
  - `PATCH /api/cases/{id}/status`
  - `PATCH /api/cases/{id}/assign`
- **Providers**:
  - `POST /api/providers`
  - `GET /api/providers/nearby?lat=19.0760&lon=72.8777&radiusKm=15&type=HOSPITAL`
- **Communications**:
  - `POST /api/communications/whatsapp/send`
  - `POST /api/communications/email/send`
- **Calls**: `POST /api/calls/initiate`
- **Invoices**: `POST /api/invoices`
- **Reporting**:
  - `GET /api/reports/cases`
  - `GET /api/dashboard/metrics`
- **Audit**: `GET /api/audit-logs`

## Interview Guide

### Role & Experience Summary

As a backend developer on CareTrack, you contributed to:

1. Spring Boot backend development for a medical assistance CRM & MIS platform
2. Case lifecycle management implementation
3. Third-party integrations (Maps, WhatsApp, Email, VoIP)
4. Role-based access control and audit logging
5. MIS reporting and dashboard APIs
6. Collaboration in a production SaaS environment

### Key Resume Points

- Contributed to the design and development of a scalable CRM & MIS backend using Spring Boot
- Worked on end-to-end case lifecycle features including case intake, assignment, tracking, and closure
- Developed and maintained RESTful APIs for case coordination, provider management, invoicing, and reporting
- Integrated third-party services such as Google Maps (location & routing), WhatsApp, Email, and VoIP calling
- Implemented role-based access control (RBAC) and permission handling for different user roles
- Assisted in optimizing database queries and backend performance for high case volumes
- Contributed to MIS reporting and real-time dashboards for operational and management insights
- Implemented audit logs and traceability features to support compliance and insurance requirements
- Collaborated closely with frontend developers, QA, and operations teams to deliver business workflows
- Supported production deployments, bug fixes, and continuous backend improvements in a SaaS environment

### How to Explain Your Role in Interviews

**Project Context**: CareTrack is a medical assistance CRM used to manage patient cases, providers, communication, and reporting.

**Your Role**: Explain that you were one of the backend developers responsible for implementing APIs, integrations, and business logic.

**Architecture**: Spring Boot layered architecture with controllers, services, repositories, and secure REST APIs.

**Case Management**: Worked on workflows that track cases from intake to closure with real-time status updates.

**Integrations**: Helped integrate Google Maps, WhatsApp, Email, and VoIP services into the platform.

**Security**: Worked on authentication, role-based authorization, and audit logging.

**Reporting**: Contributed to MIS reports and dashboards used by operations and management.

**Collaboration**: Worked closely with team members and stakeholders to align technical implementation with business needs.

### Sample Interview Answer

"I worked as one of the backend developers on CareTrack, a medical assistance CRM built using Spring Boot. 
My responsibilities included developing REST APIs, implementing case management workflows,
integrating third-party services like Google Maps and WhatsApp, and contributing to reporting and security features.
I collaborated closely with the team to deliver a scalable, production-ready SaaS platform."

## License

This project is for demonstration purposes only.
