# Dockerfile

# jdk17 Image Start
FROM openjdk:17

# 인자 설정 - JAR_File
ARG JAR_FILE=build/libs/*.jar

# jar 파일 복제
COPY ${JAR_FILE} app.jar

COPY src/main/resources/departments.json /app/src/main/resources/

# resources 파일 복제
COPY src/main/resources/departments.json /app/src/main/resources/
# 환경 변수 설정
ENV ENV=PROD

# 실행 명령어
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]

