# Assets Service Architecture

## Final structure

- `domain.asset`: the `Asset` aggregate root and immutable value objects `AssetId`, `AssetCode`, and `Money`.
- `domain.configuration`: `AssetType` and `SubAssetType` domain models.
- `domain.exception`: domain exceptions and invariant failures.
- `domain.ports.in`: input use-case contracts.
- `domain.ports.out`: output repository contracts, including `IAssetRepository`.
- `application.service`: application orchestration and DTO mapping calls.
- `infrastructure.adapters.in.rest`: REST controllers, request/response DTOs, and REST mappers.
- `infrastructure.adapters.out.persistence`: JPA entities, persistence mappers, Spring Data repositories, and `AssetRepositoryImpl`.

The dependency direction is `infrastructure -> application -> domain`. The domain has no Spring or JPA imports. DTOs and persistence entities remain outside the domain, with mappers translating at each boundary.

## Renamed paths

- `domain/activos/Asset.java` -> `domain/asset/Asset.java`
- `domain/parametrizacion/AssetType.java` -> `domain/configuration/AssetType.java`
- `domain/parametrizacion/SubAssetType.java` -> `domain/configuration/SubAssetType.java`
- `domain/ports/out/AssetRepositoryPort.java` -> `domain/ports/out/IAssetRepository.java`
- `persistence/repository/SpringDataActivoRepository.java` -> removed; `AssetJpaRepository` is the single Spring Data repository
- `persistence/repository/adapter/AssetRepositoryAdapter.java` -> `persistence/repository/adapter/AssetRepositoryImpl.java`

All Java packages, types, methods, fields, comments, SQL identifiers, and build metadata use English names. The REST resources remain under `/api/v1/assets`; the deactivation resource is `/deactivate`.

## Domain rules

Use `Asset.create(...)` for new aggregates. It creates an `AssetId`, validates required fields, and wraps the code in `AssetCode`. Use `Asset.restore(...)` only when rehydrating persistence data. `AssetCode` rejects blank values and values longer than 50 characters; `Money` rejects missing values and negative amounts.

## Adding an entity

1. Add the pure aggregate and value objects under `domain/<bounded-context>`.
2. Put repository interfaces in `domain/ports/out` and input contracts in `domain/ports/in`.
3. Add application services that depend only on domain ports and application DTOs.
4. Add REST DTOs/controllers and mappers under the inbound adapter.
5. Add a JPA entity, Spring Data interface, persistence mapper, and `*RepositoryImpl` under the outbound adapter.
6. Add a Flyway migration and focused domain/application tests.

## Validation commands

```text
mvn clean compile
mvn test
mvn spring-boot:run
```

The source compilation and tests pass. Application startup reaches Tomcat but requires PostgreSQL at `localhost:5433` with the configured database; without that database Flyway prevents the context from starting and REST endpoints cannot respond.