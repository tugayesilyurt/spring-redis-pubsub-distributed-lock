# Spring Boot,Redis pubsub and Distributed lock example

Technologies
------------
- `Spring Boot`
- `MySQL` 
- `Redis`
- `Redisson Client`
- `Redisson Distributed Lock`
- `Docker`
- `Docker-Compose`
- `Lombok`
- `Spring Retry`
- `CommandLineRunner`

## Run the System
We can easily run the whole with only a single command:

* `docker-compose up -d`

#### 5: Starting redis-publisher`

```shell
./redis-publisher
mvn spring-boot:run
```

#### 5: Starting redis-subscriber-one`

```shell
./redis-subscriber-one
mvn spring-boot:run
```

#### 5: Starting redis-subscriber-two`

```shell
./redis-subscriber-two
mvn spring-boot:run
```

```java
RLock lock = redisson.getLock("update-stock-lock");
lock.lock(4, TimeUnit.SECONDS);
lock.unlock();
```

```java
@Retryable(value = org.redisson.client.RedisTimeoutException.class, maxAttempts = 2, backoff = @Backoff(delay = 2000))
```

- **System Design**

![System Design](https://github.com/tugayesilyurt/spring-redis-pubsub-distributed-lock/blob/main/assets/design.PNG)

