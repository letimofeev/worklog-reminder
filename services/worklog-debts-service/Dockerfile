FROM openjdk:11
ARG PROJECT_NAME
ARG PROJECT_VERSION
COPY build/libs/$PROJECT_NAME-$PROJECT_VERSION.jar worklog-reminder.jar
ENTRYPOINT ["java", "-jar", "/worklog-reminder.jar"]
EXPOSE 8100
