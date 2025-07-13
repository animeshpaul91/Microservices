# Microservices Learning Project

A comprehensive collection of microservices implementations and Apache Kafka examples for learning distributed systems architecture.

## 📋 Project Overview

This repository contains multiple learning modules covering:
- **Spring Boot Microservices** - Complete microservices architecture with service discovery
- **Apache Kafka** - Event streaming platform examples and implementations
- **Spring Framework Fundamentals** - Core Spring concepts and dependency injection

## 🏗️ Architecture

### Spring Boot Microservices Workshop

A movie catalog system demonstrating microservices patterns:

```
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│  Movie Catalog  │────│   Movie Info     │    │  Ratings Data   │
│    Service      │    │    Service       │    │    Service      │
│   (Port: 8081)  │    │  (Port: 8082)    │    │  (Port: 8083)   │
└─────────────────┘    └──────────────────┘    └─────────────────┘
         │                       │                       │
         └───────────────────────┼───────────────────────┘
                                 │
                    ┌──────────────────┐
                    │ Eureka Discovery │
                    │     Server       │
                    │   (Port: 8761)   │
                    └──────────────────┘
                                 │
                    ┌──────────────────┐
                    │ Spring Cloud     │
                    │ Config Server    │
                    │   (Port: 8888)   │
                    └──────────────────┘
```

### Services

#### 🎬 Movie Catalog Service
- **Purpose**: Aggregates movie information and user ratings
- **Endpoints**: `/catalog/{userId}` - Returns personalized movie catalog
- **Dependencies**: Movie Info Service, Ratings Data Service
- **Features**: Service composition, circuit breaker patterns

#### 🎭 Movie Info Service  
- **Purpose**: Provides detailed movie information
- **Endpoints**: `/movies/{movieId}` - Returns movie details
- **External API**: Integrates with The Movie Database (TMDb) API
- **Features**: External API integration, data transformation

#### ⭐ Ratings Data Service
- **Purpose**: Manages user movie ratings
- **Endpoints**: 
  - `/ratingsdata/movies/{movieId}` - Get movie rating
  - `/ratingsdata/user/{userId}` - Get user's ratings
- **Features**: Mock data service for ratings

#### 🔍 Eureka Discovery Server
- **Purpose**: Service registry and discovery
- **Port**: 8761
- **Features**: Service registration, health monitoring, load balancing

#### ⚙️ Spring Cloud Config Server
- **Purpose**: Centralized configuration management
- **Port**: 8888
- **Features**: External configuration, environment-specific configs

## 🚀 Apache Kafka Examples

### Kafka Basics
- **Producer Examples**: Simple producer, producer with callbacks, keyed messages
- **Consumer Examples**: Basic consumer, consumer groups, manual offset management
- **Location**: `Apache_Kafka/kafka-beginners-course/kafka-basics/`

### Real-World Applications
- **Twitter Producer**: Streams Twitter data to Kafka topics
- **Elasticsearch Consumer**: Consumes Kafka messages and indexes to Elasticsearch
- **Stream Processing**: Kafka Streams for real-time tweet filtering

## 🛠️ Technology Stack

### Microservices
- **Spring Boot 2.x** - Application framework
- **Spring Cloud** - Microservices toolkit
- **Netflix Eureka** - Service discovery
- **Spring Cloud Config** - Configuration management
- **Maven** - Build tool

### Apache Kafka
- **Apache Kafka** - Event streaming platform
- **Kafka Streams** - Stream processing library
- **Elasticsearch** - Search and analytics engine
- **Twitter API** - Real-time data source

### Spring Framework
- **Spring Core** - Dependency injection
- **Spring Context** - Application context management

## 🚀 Getting Started

### Prerequisites
- Java 11 or higher
- Maven 3.6+
- Apache Kafka (for Kafka examples)
- Elasticsearch (for consumer examples)

### Running the Microservices

1. **Start Eureka Discovery Server**
   ```bash
   cd spring-boot-microservices-workshop/eureka-discovery-server
   mvn spring-boot:run
   ```
   Access at: http://localhost:8761

2. **Start Config Server**
   ```bash
   cd spring-boot-microservices-workshop/spring-cloud-config-server
   mvn spring-boot:run
   ```

3. **Start Individual Services**
   ```bash
   # Ratings Data Service (Port 8083)
   cd spring-boot-microservices-workshop/ratings-data-service
   mvn spring-boot:run
   
   # Movie Info Service (Port 8082)
   cd spring-boot-microservices-workshop/movie-info-service
   mvn spring-boot:run
   
   # Movie Catalog Service (Port 8081)
   cd spring-boot-microservices-workshop/movie-catalog-service
   mvn spring-boot:run
   ```

### Testing the Application

Get movie catalog for user:
```bash
curl http://localhost:8081/catalog/1
```

### Running Kafka Examples

1. **Start Kafka**
   ```bash
   # Start Zookeeper
   bin/zookeeper-server-start.sh config/zookeeper.properties
   
   # Start Kafka Server
   bin/kafka-server-start.sh config/server.properties
   ```

2. **Run Examples**
   ```bash
   cd Apache_Kafka/kafka-beginners-course/kafka-basics
   mvn compile exec:java -Dexec.mainClass="gettingstarted.producers.ProducerDemo"
   ```

## 📚 Learning Objectives

### Microservices Patterns
- Service decomposition
- API Gateway pattern
- Service discovery
- Configuration management
- Circuit breaker pattern
- Service composition

### Event-Driven Architecture
- Event streaming
- Producer/Consumer patterns
- Stream processing
- Event sourcing concepts

### Spring Ecosystem
- Dependency injection
- Bean lifecycle management
- Configuration management
- Auto-configuration

## 🔧 Configuration

### Application Ports
- **Eureka Server**: 8761
- **Config Server**: 8888
- **Movie Catalog Service**: 8081
- **Movie Info Service**: 8082
- **Ratings Data Service**: 8083

### External Dependencies
- **TMDb API**: Requires API key for movie information
- **Kafka**: Required for streaming examples
- **Elasticsearch**: Required for consumer examples

## 📖 Key Concepts Demonstrated

### Microservices
- **Service Discovery**: Automatic service registration and lookup
- **Load Balancing**: Client-side load balancing with Ribbon
- **Fault Tolerance**: Circuit breaker patterns with Hystrix
- **Configuration**: Externalized configuration management

### Event Streaming
- **Pub/Sub Messaging**: Producer/consumer decoupling
- **Stream Processing**: Real-time data transformation
- **Scalability**: Partitioned topics for horizontal scaling

## 🤝 Contributing

This is a learning project. Feel free to experiment with:
- Adding new microservices
- Implementing additional Kafka patterns
- Exploring different Spring features
- Adding monitoring and observability

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Based on Java Brains microservices tutorial
- Apache Kafka documentation and examples
- Spring Boot and Spring Cloud communities