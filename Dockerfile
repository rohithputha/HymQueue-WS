# Use the base JDK image to build and run the Java application
FROM openjdk:11-jdk-slim
# Install Maven
RUN apt-get update && \
    apt-get install -y maven

# Set the working directory inside the container
WORKDIR /app

# Copy the entire content of the src folder to the container's working directory
COPY /src /app/src
COPY pom.xml /app

# Build the project with Maven
RUN mvn package

# Expose the port that the Java application listens on (change this to your application's port)
EXPOSE 3456


# Run the Java application
CMD ["java", "-jar", "target/HymQueue-ws.jar"]
