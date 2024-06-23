FROM eclipse-temurin:17-jdk-alpine as builder

# WORKDIR /app
# COPY . ./

# RUN chmod +x ./gradlew
# RUN ./gradlew clean bootJar --no-daemon
# RUN ./gradlew --stop

# FROM eclipse-temurin:17-jre-alpine

RUN apk update && apk upgrade
RUN apk add --no-cache ca-certificates && update-ca-certificates
RUN apk add --no-cache tzdata && cp /usr/share/zoneinfo/Asia/Seoul /etc/localtime
RUN echo "Asia/Seoul" > /etc/timezone

# COPY --from=builder /app/build/libs/app.jar .
COPY build/libs/app.jar .

EXPOSE 80

ENTRYPOINT ["java" , "-jar" , "app.jar"]