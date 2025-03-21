
# Use the official OpenJDK 17 image from Docker Hub
FROM openjdk:17
# Set working directory inside container
WORKDIR /app
#Copy the compiled Java application JAR file into the container
COPY ./target/ecomm_java_backed.jar /app
#Expose the port the spring boot application will run on
EXPOSE 5454
#Command to run the application
CMD [ "java", "-jar", "course-service.jar" ]