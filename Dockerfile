FROM eclipse-temurin:21-jre-alpine

WORKDIR /app


COPY target/*.jar dockerfile.jar

EXPOSE 9191

ENTRYPOINT ["java", "-jar", "dockerfile.jar"]
