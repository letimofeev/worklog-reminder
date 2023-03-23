FROM openjdk:11
ARG PROJECT_NAME
ARG PROJECT_VERSION
COPY build/libs/$PROJECT_NAME-$PROJECT_VERSION.jar worklog-debt-notification.jar
ENTRYPOINT ["java", "-jar", "/worklog-debt-notification.jar"]
EXPOSE 8400
