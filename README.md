### Kindless Microservice

- spring-boot
  - [x] spring-webmvc
  - [x] spring-data-jpa
- spring-cloud
  - [x] service discovery
  - [ ] gateway
  - [x] load balancing
  - [ ] distributed transaction
  - [ ] stream/messaging
  - [x] configuration
  - [x] circuit breaker
  - [x] tracing
  - [ ] task(scheduling)
  - [ ] security

- service discovery
  - eureka
  - zookeeper
  - consul
  - alicloud nacos
- gateway
  - spring-gateway
  - netflix zuul
- load balancing
  - ribbon
- distributed transaction
  - spring-intergation: just distributed lock(zookeeper, redis)
  - alicloud fescar
- stream/messaging
  - spring-stream
  - alicloud rocketmq
- configuration
  - alicloud nacos
  - spring-cloud-config
  - alicloud acm
- circuit breaker
  - alicloud sentinel
  - netflix hystrix
  - resilience4j
- tracing
  - zipkin
  - skywalking
  - cat
  - ELK Stack(Elasticsearch, Logstash, Kibana)
- task
  - spring-cloud-task
  - alicloud schedulerX
- security
  - spring-cloud-security

> -javaagent:/path/to/skywalking-agent.jar -Dskywalking.agent.service_name=${application.name}

> java -jar -Dspring.cloud.bootstrap.location=file:/path/to/bootstrap.yaml