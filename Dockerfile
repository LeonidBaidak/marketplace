FROM openjdk:17-alpine
VOLUME /app
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar","spring.profiles.active=dev"]
EXPOSE 8080