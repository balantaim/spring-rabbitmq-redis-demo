# AsyncBridge

## Description

A lightweight, event-driven microservice system built with Spring and Java 21. The `producer` service asynchronously publishes messages via RabbitMQ, while the `consumer` stores processed data in Redis. All components run in Docker containers with security-hardened, non-root JRE images.

## Software

<b>Tools:</b> Java 21, Spring, RabbitMQ, Redis, Docker

## Setup - Local

Startup RabbitMQ and Redis containers

```bash
cd rabbitmq-producer/
```
```bash
docker compose up -d
```

Startup `rabbitmq-consumer` and `rabbitmq-producer`. You could use the script inside the project's directory:

```bash
./mvnw spring-boot:run
```

## Setup - Via Docker network (Recommended)

Build from Docker file inside the root directory

```bash
docker compose up -d
```

- RabbitMQ UI http://localhost:15672
- Redis UI (redisinsight) http://localhost:5540
- REST Controller endpoint http://localhost:8080/api/v1/message

## Test the application

```bash
curl -X POST http://localhost:8080/api/v1/message -H "Content-Type: text/plain" -d "Hello App :)"
```

### Enter to Redis CLI via Docker

```bash
docker exec -it redis redis-cli
```

### Get all keys inside redis

```bash
KEYS *
```
### Get event by its id (replace * with your id)

```bash
GET event:*********
```



