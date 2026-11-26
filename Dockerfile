
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY target/myDokerJar.jar app.jar

EXPOSE 9191

ENTRYPOINT ["java", "-jar", "app.jar"]
