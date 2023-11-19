# FROM --platform=linux/arm64 adoptopenjdk:17-jdk-hotspot
# WORKDIR /profile-service-app

# # Copy the JAR file into the container at /app
# COPY target/businessprofileservice-0.0.1-SNAPSHOT.jar app/businessprofileservice.jar

# # Expose port 8080
# EXPOSE 8080

# # Specify the command to run on container startup
# ENTRYPOINT ["java", "-jar", "/profile-service-app/app/businessprofileservice.jar"]
FROM eclipse-temurin:17-jdk-focal
 
WORKDIR /app
 
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
 
COPY src ./src
 
CMD ["./mvnw", "spring-boot:run"]