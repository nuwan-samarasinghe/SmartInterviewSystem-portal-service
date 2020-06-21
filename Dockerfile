# Start with a base image containing Java runtime
FROM openjdk:8-jdk-alpine

# Make port 8080 available to the world outside this container
EXPOSE 8182

# The application's jar file
COPY "/target/portal-service-0.0.1-SNAPSHOT.jar" "/usr/app/"

WORKDIR "/usr/app"

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=dev","-jar","portal-service-0.0.1-SNAPSHOT.jar"]
