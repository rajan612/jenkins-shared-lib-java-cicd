FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY ./target/*.jar /
CMD ["java","-jar", "app.jar"]