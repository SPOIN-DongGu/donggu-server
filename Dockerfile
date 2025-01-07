FROM openjdk:17

WORKDIR /app

ARG JAR_PATH=build/libs

COPY ${JAR_PATH}/*.jar /app/donggu.jar

ENTRYPOINT ["java", "-jar", "donggu.jar"]

EXPOSE 8080