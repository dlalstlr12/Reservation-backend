FROM openjdk:17-jdk-slim

WORKDIR /app

# JAR 파일 복사 (전체 경로 지정)
COPY build/libs/reservation-0.0.1-SNAPSHOT.jar /app.jar

EXPOSE 8080

# JAR 파일 실행 권한 추가
RUN chmod +x /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]