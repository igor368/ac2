FROM eclipse-temurin:17-jdk

# Set the working directory in the container
WORKDIR /ac2

# Copy the JAR file into the container at /educacaoGamificada
COPY target/*.jar /ac2/ac2-0.0.1-SNAPSHOT.jar

# Expose the port that your application will run on
EXPOSE 8585

# Specify the command to run on container start
CMD ["java", "-jar", "ac2-0.0.1-SNAPSHOT.jar"]