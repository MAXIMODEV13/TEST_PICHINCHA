FROM eclipse-temurin:11-alpine

ENV PORT=8080

COPY target/springboot-servicio-tipo-cambio-0.0.1-SNAPSHOT.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]