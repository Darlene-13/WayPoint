# WayPoint Backend

WayPoint is a job application tracker. The backend provides the API used by the Angular application in `ui/waypoint-ui`.

## Current progress

The Company feature is complete enough for end-to-end testing. The following endpoints have been tested successfully against the local PostgreSQL database:

```text
GET    /api/companies
POST   /api/companies
GET    /api/companies/{id}
PUT    /api/companies/{id}
DELETE /api/companies/{id}
```

The tests covered creating multiple companies, retrieving the company list, retrieving a company by its ID, updating a company, and deleting a company.

The Job Application API is now also available for testing:

```text
POST   /api/applications
GET    /api/applications
GET    /api/applications/{id}
PATCH  /api/applications/{id}/stage
DELETE /api/applications/{id}
```

Creating an application requires the ID of an existing company. Stage changes also create a stage history record. When using Postman, send application creation and stage-change bodies as JSON with the `Content-Type: application/json` header.

The project uses PostgreSQL and Flyway migrations. The initial schema is in:

```text
src/main/resources/db/migration/V1__create_initial_schema.sql
```

Authentication is not implemented yet. The current development security configuration permits requests so the API can be tested from Postman and the Angular development server. This must be replaced with real authentication before deployment.

## Running locally

Set the database variables in the local environment before starting the backend:

```bash
set -a
source .env
set +a
./mvnw spring-boot:run
```

The backend runs on port `8080` by default.

The Angular frontend runs in the separate project located at `ui/waypoint-ui`.

## Next step

Continue testing the Job Application endpoints, including creation, retrieval, stage changes, filtering by stage, and deletion. The frontend still needs its API wiring and proxy configuration checked against the running backend.

## Project structure

```text
src/main/java                  Spring Boot backend
src/main/resources/db/migration Flyway database migrations
ui/waypoint-ui                 Angular frontend
```
