# hexagonal-with-quarkus

A Quarkus-based implementation of https://gitlab.com/beyondxscratch/hexagonal-architecture-java-springboot
with some additional features.

Must watch https://www.youtube.com/watch?v=-dXN8wkN0yk&t=2212s

## Build it

```shell
mvn clean install -DskipITs=false
```

## Run it (dev mode)

```shell
mvn clean package
cd app
mvn quarkus:dev
```

## Run it (prod mode)

```shell
mvn clean install
java -jar app/target/quarkus-app/quarkus-run.jar
```

## Test it

### Assemble a rescue fleet

```shell
curl -X POST -H "Content-Type: application/json" -d '{"numberOfPassengers": 20}' http://localhost:8080/rescueFleets

# {"starships":[{"name":"Naboo star skiff","capacity":3},{"name":"Millennium Falcon","capacity":6},{"name":"Slave 1","capacity":6},{"name":"Scimitar","capacity":6}]}
```

### Rebel bases

```shell
# List rebel bases
curl http://localhost:8080/rebelBases
# [{"name":"Tatooine","riskLevel":"SECURE","shipCapacity":10},{"name":"Dagobah","riskLevel":"COMPROMISED","shipCapacity":2}, ...]

# Add a rebel base
curl -v -X POST -H "Content-Type: application/json" -d '{"name": "My rebel base","riskLevel":"SECURE","shipCapacity":50}' http://localhost:8080/rebelBases

# Find a rebel base for rescue fleet
curl -v -X POST -H "Content-Type: application/json" -d '{"starshipNames": ["X-wing"],"riskLevel": "SECURE", "maxDurationInHours": 1}' http://localhost:8080/rebelBases/rescueCandidates
# [{"name":"Tatooine","riskLevel":"SECURE","shipCapacity":10},{"name":"Endor","riskLevel":"SECURE","shipCapacity":3},{"name":"My rebel base 2","riskLevel":"SECURE","shipCapacity":50}]
```

## Hexagonal Architecture Overview

This project demonstrates the implementation of Hexagonal Architecture (also known as Ports and Adapters) using Quarkus framework. The key aspects include:

- **Domain-Centric Design**: The business logic is isolated in the core domain, free from external dependencies
- **Ports**: Defined interfaces that specify how the application interacts with the outside world
- **Adapters**: Concrete implementations that connect the application to external systems
  - Primary/Driving Adapters: Handle incoming requests (REST APIs, CLI)
  - Secondary/Driven Adapters: Handle outgoing requests (Database, External Services)

The hexagonal structure ensures:
- Clear separation of concerns
- Business logic independence from technical implementations
- Easier testing through adapter substitution

## Project Implementation

This project implements the hexagonal architecture with the following structure:

### Core Domain (Hexagon)
- Contains pure business logic and domain models
- No dependencies on external frameworks or libraries
- Defines ports (interfaces) for both incoming and outgoing operations

### Primary Adapters (Driving Side)
- REST API endpoints using Quarkus JAX-RS
- Each adapter uses ports to communicate with the domain

### Secondary Adapters (Driven Side)
- REST Client implementation to interact with external services
- Implementation of repository interfaces defined in the domain

### Key Benefits in This Implementation
- Domain logic can be tested in isolation
- Easy to swap implementations (e.g., switch external service providers or mock them)
- Clear boundaries between technical infrastructure and business rules
- Dependency injection handled by Quarkus CDI
- Flexible evolution of both domain and technical layers