# Microservices & Distributed Systems Lab

A single workstation of Spring, Kafka, gRPC, and WebSocket experiments built while following various Udemy courses. Each folder is a self-contained lab that you can open in your IDE of choice and run independently.

## Repository Layout

| Path | What it contains |
| --- | --- |
| `spring-boot-microservices-workshop/` | Movie catalog microservices demo with Eureka, Config Server, and three business services. |
| `Apache_Kafka/` | Kafka notes (`README.md`, `Schema_Registry.png`) and the `kafka-beginners-course` Maven multi-module project (basics, Twitter producer, Elasticsearch consumer, Kafka Streams). |
| `Complete SpringBoot/` | Two Spring Boot 3.5 projects (`learn-spring-framework`, `spring-dependency-injection`) plus archived course starter code. |
| `gRPC/Protocol Buffers/protobufs/` | Maven project that organizes `.proto` examples (sections 1‑5) and generates Java + gRPC stubs. |
| `Websockets/` | Two Node.js demos: a raw `ws` example and a Socket.IO chat app. |
| `master-spring-and-spring-boot-main.zip` | Archived upstream course code for reference. |

## Prerequisites

- Java 21 and Maven 3.9+
- Node.js 18+ with npm (for the WebSocket demos)
- Docker or local installs for Kafka/ZooKeeper and Elasticsearch if you want to run the streaming labs
- A TMDb API key for real movie data in `movie-info-service`
- Optional: Twitter API credentials for the Kafka Twitter producer

## Spring Boot Microservices Workshop (`spring-boot-microservices-workshop/`)

This folder mirrors the Java Brains movie catalog tutorial and is currently the most complete end-to-end example.

### Services & Ports

| Service | Path | Port | Notes |
| --- | --- | --- | --- |
| Eureka Discovery Server | `eureka-discovery-server` | `8761` | Register all other services; visit `http://localhost:8761`. |
| Spring Cloud Config Server | `spring-cloud-config-server` | `8888` | Points at this Git repo (`spring.cloud.config.server.git.search-paths=config/spring-boot-config`). Export `GIT_USERNAME` / `GIT_PASSWORD` before running. |
| Ratings Data Service | `ratings-data-service` | `8083` | Returns mock ratings lists (`/ratingsdata/movies/{movieId}`, `/ratingsdata/user/{userId}`). |
| Movie Info Service | `movie-info-service` | `8082` | Wraps TMDb lookups; requires `TMDB_API_KEY`. |
| Movie Catalog Service | `movie-catalog-service` | `8081` | Aggregates ratings + movie metadata for `/catalog/{userId}` and exposes Hystrix stream (`/actuator/hystrix.stream`). |
| Spring Boot Config Client | `spring-boot-config` | — | Sample app showing profile-specific YAML fetched through the Config Server. |

### Boot Order

```bash
# 1. Service registry
cd spring-boot-microservices-workshop/eureka-discovery-server
mvn spring-boot:run

# 2. Config server
cd ../spring-cloud-config-server
mvn spring-boot:run

# 3. Business services (in separate terminals)
cd ../ratings-data-service    && mvn spring-boot:run
cd ../movie-info-service      && mvn spring-boot:run
cd ../movie-catalog-service   && mvn spring-boot:run
```

Verify the flow:

```bash
curl http://localhost:8081/catalog/1
```

### Centralized Configuration

`spring-boot-config/src/main/resources` contains `application-{env}.yml` files that the Config Server serves. Update those files to change values without redeploying the services. Because the Config Server is backed by this Git repo, remember to push commits if you want remote services (or colleagues) to pick up the changes.

## Apache Kafka Track (`Apache_Kafka/`)

- `README.md` is a living cheat-sheet that covers brokers, partitions, replication, CLI commands, idempotent producers, offset resets, and schema registry basics (with `Schema_Registry.png`).
- `kafka-beginners-course` is a Maven multi-module project:
  - `kafka-basics`: producer and consumer demos (simple, with callbacks, keys, and custom configs).
  - `kafka-consumer-elasticsearch`: ingests Kafka messages into Elasticsearch indices.
  - `kafka-producer-twitter`: streams tweets into Kafka topics (plug in your credentials).
  - `kafka-streams-filter-tweets`: Kafka Streams application that filters tweets into a derived topic.

### Running an example

```bash
# Start ZooKeeper and Kafka locally (or via Docker) first.

cd Apache_Kafka/kafka-beginners-course/kafka-basics
mvn compile exec:java -Dexec.mainClass="gettingstarted.producers.ProducerDemo"
```

Use the CLI commands in `Apache_Kafka/README.md` to inspect topics, consumer groups, or to reset offsets while experimenting.

## Spring Boot Fundamentals (`Complete SpringBoot/`)

Two starter projects that pair with the “Spring Boot Complete” course:

- `learn-spring-framework`: minimal Spring Boot 3.5 project that demonstrates component scanning, `ApplicationContext` usage, and record-based beans.
- `spring-dependency-injection`: variations of constructor, setter, and field injection plus primary/qualifier examples.

Each project bundles the Maven wrapper, so you can run:

```bash
cd "Complete SpringBoot/learn-spring-framework"
./mvnw spring-boot:run
```

## gRPC & Protocol Buffers (`gRPC/Protocol Buffers/protobufs/`)

A Maven project that organizes `.proto` examples across five sections (`sec-01` … `sec-05`). The `protobuf-maven-plugin` (protoc `3.25.5`, gRPC `1.72.0`) generates Java sources under `target/generated-sources`.

Typical workflow:

```bash
cd "gRPC/Protocol Buffers/protobufs"
mvn clean compile   # generates Java + gRPC stubs
mvn test            # runs the accompanying JUnit 5 specs
```

Inspect the `src/main/proto` tree to see examples of maps, enums, one-ofs, well-known types, and message composition.

## WebSocket Demos (`Websockets/`)

Two Node.js labs that showcase real-time communication patterns:

- `Simple_Websockets`: barebones WebSocket server built with the `ws` library plus a `client.html` tester.
- `Chat_Application`: Socket.IO-based multi-user chat with both server (`index.js`) and in-browser client (`client.js`).

Install dependencies and start either example:

```bash
cd Websockets/Simple_Websockets
npm install
npx nodemon index.js
```

Then open the corresponding client (`client.html` or `client.js` via a bundler) to exchange messages.

## Contributing & Next Steps

- Extend the Spring microservices with API gateway, tracing, or persistence.
- Add new Kafka processors (e.g., KSQL or Kafka Connect sinks).
- Experiment with unary vs. streaming RPCs by adding `.proto` files in the gRPC project.
- Improve the WebSocket demos with authentication or persistence.

Feel free to open the lab you are interested in, run the commands above, and iterate. Each folder intentionally keeps dependencies isolated so you can focus on one technology at a time.
