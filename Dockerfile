FROM openjdk:8-jdk-alpine
ADD target/kindless-0.0.1-SNAPSHOT.jar kindless.jar
EXPOSE 9000
ENTRYPOINT ["java", "-jar", "kindless.jar"]