FROM eclipse-temurin:17-jre

COPY build/libs/*.jar app.jar

ENTRYPOINT ["java", "--add-exports=java.base/sun.nio.ch=ALL-UNNAMED", "-jar", "/app.jar"]