FROM openjdk:11
ARG PROJECT_NAME
ARG PROJECT_VERSION
COPY build/libs/$PROJECT_NAME-$PROJECT_VERSION.jar worklog-debt.jar
ENTRYPOINT ["java", "-jar", "/worklog-debt.jar"]
EXPOSE 8100
