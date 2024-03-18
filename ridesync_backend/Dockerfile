# Use an appropriate base image for Java
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Install Maven
RUN apk update && \
    apk add --no-cache maven

# Copy the Maven build file
COPY ./pom.xml .

# Copy the source code
COPY ./src ./src

ENV PORT=8073

# Expose the port on which the React app will run
EXPOSE $PORT

# Build the application
RUN mvn clean package -X

# Command to run the application
CMD ["java", "-jar", "target/ridesync-0.0.1-SNAPSHOT.jar"]
