FROM maven:3.8.3-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM maven:3.6.3-openjdk-17-slim
COPY --from=build /target/collection_web_application-0.0.1-SNAPSHOT.jar collection_web_application.jar
EXPOSE 2631
ENTRYPOINT["java","-jar","collection_web_application.jar"]










