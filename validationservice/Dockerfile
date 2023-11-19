# FROM openjdk:17-oracle
# WORKDIR /validation-service-app
# COPY target/validationservice-0.0.1-SNAPSHOT.jar app/validationservice.jar

# # RUN rm -rf data

# EXPOSE 1235

# # Specify the command to run on container startup
# ENTRYPOINT ["java", "-jar", "/validation-service-app/app/validationservice.jar"]

FROM eclipse-temurin:17-jdk-focal
 
WORKDIR /app
 
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
 
COPY src ./src
 
CMD ["./mvnw", "spring-boot:run"]