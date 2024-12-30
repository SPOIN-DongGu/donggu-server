FROM openjdk:17

WORKDIR /app

ARG JAR_PATH=build/libs/donggu-0.0.1-SNAPSHOT.jar

COPY ${JAR_PATH} /app/donggu.jar

ENTRYPOINT ["java", "-jar", "donggu.jar"]

EXPOSE 8080