# FROM openjdk:17-oracle
# WORKDIR /cache-service-app
# COPY target/cacheservice-0.0.1-SNAPSHOT.jar app/cacheservice.jar

# # RUN rm -rf data

# # Expose port 8080
# EXPOSE 1236

# # Specify the command to run on container startup
# ENTRYPOINT ["java", "-jar", "/cache-service-app/app/cacheservice.jar"]

FROM eclipse-temurin:17-jdk-focal
 
WORKDIR /app
 
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
 
COPY src ./src
 
CMD ["./mvnw", "spring-boot:run"]