# Базовый образ с JDK 17
FROM eclipse-temurin:17-jre

# Копируем собранный jar в контейнер
COPY build/libs/*.jar /app.jar

# Команда запуска приложения
ENTRYPOINT ["java", "-jar", "/app.jar"]