FROM gradle:8.5-jdk17 AS build

ARG DB_URL
ENV DB_URL=$DB_URL
ARG DB_NAME
ENV DB_NAME=$DB_NAME
ARG DB_PASSWORD
ENV DB_PASSWORD=$DB_PASSWORD

COPY . /app
WORKDIR /app
RUN apt-get update && apt-get install -y maven
RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test

FROM openjdk:17 AS final
COPY --from=build /app/build/libs/*.jar /app/greenroute.jar
EXPOSE 80
CMD ["java", "-jar", "/app/greenroute.jar"]
