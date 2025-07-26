# Используем базовый образ с JDK 17
FROM eclipse-temurin:17-jre

# Копируем собранный jar в контейнер
COPY build/libs/*.jar app.jar

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "/app.jar"]