FROM eclipse-temurin:21-jdk-jammy

COPY target/*.jar app.jar

# 시간 수정
RUN sh -c 'touch /app.jar'

EXPOSE 8080

#ENTRYPOINT [ "java", "-jar", "/app.jar" ]
ENTRYPOINT [ "sh", "-c", "java -jar /app.jar" ]
