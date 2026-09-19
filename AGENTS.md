# AGENTS.md

Spring Boot 4 (parent 4.1.1) / Spring Data JPA demo project on **Java 25** (enforced by the
maven-enforcer plugin). Single Maven module, package `ch.dboeckli.guru.jpa.sdjpa.inheritance`. It
demonstrates the JPA inheritance strategies (single table, table per class, joined, mapped
superclass) against an in-memory H2 database. App port `8080`.

## Build & test commands

- Full build: `./mvnw clean verify` — format checks, unit tests (`*Test`, surefire), Helm lint/template.
- Unit tests only: `./mvnw test`. Single test: `./mvnw test -Dtest=CarRepositoryTest#methodName`.
- `./mvnw clean install` additionally builds the Docker image and packages the Helm chart into
  `target/helm/repo/`. Skip the Docker build with `-Dskip.docker.build=true`.
- `-Dskip.start.stop.springboot=true` skips the in-build app boot (spring-boot:start/stop).
- Run locally: `./mvnw spring-boot:run` (H2 in-memory, no Docker needed).

After changing code, always verify: run the relevant Maven goal above and report its output
(evidence, not just "done").

## Sandbox build quirk (background)

This sandbox mounts the repo via filesystem passthrough, which blocks symlinks — Spotless's
`npm install` (prettier) would fail with `EPERM` unless npm skips bin links. The sandbox kit sets
`npm_config_bin_links=false` globally (`spec.yaml` → `environment.variables`), so no manual export
is needed here. On a normal host (Windows/CI) this does not apply either.

## Formatting is enforced (fails the `validate` phase)

- Java: Spring Java Format → fix with `./mvnw spring-javaformat:apply`.
- Everything else (pom.xml, `**/*.md`, json, `src/main/resources/application*.yaml`, `**/*.sh`):
  Spotless → fix with `./mvnw spotless:apply`.
- Spotless flexmark also formats markdown, so this file and any `.md` edits must stay flexmark-clean;
  run `./mvnw spotless:apply` after editing markdown.

## Test conventions

- Naming matters: `*Test` = unit (surefire), `*IT` = integration (failsafe).
- Repository tests are `@DataJpaTest` against H2, grouped by inheritance strategy
  (`repository/{singletable,tableperclass,joined,mappedsuperclass}`).
- A custom `TestClassOrderer` sorts test classes; `LocaleExtension` forces `Locale.US`.

## Architecture

- `domain` (+ `domain/{singletable,tableperclass,joined,mappedsuperclass}`) entities, one package per
  inheritance strategy; `repository` mirrors that structure; `bootstrap/DataInitializer` seeds data.
- H2 runs in MySQL-compat mode (`MODE=MYSQL`).

## Deploy / CI

- Deployment is Helm-only: chart in `helm-charts/` (no subcharts), packaged to `target/helm/repo/`,
  release name = artifactId, namespace `sdjpa-inheritance`.
- CI (`.github/workflows/`): `maven-build.yml` builds + deploys snapshots and triggers
  `deploy-and-test-cluster.yml`; `release.yml` runs `mvn release:prepare release:perform` on
  main/master only (version must be `-SNAPSHOT`); SonarCloud analysis runs in the `analyze` job.
- Dependency updates are managed via `.github/renovate.json`; validate changes with
  `renovate-config-validator`.
