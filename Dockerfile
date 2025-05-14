# Etapa de build
FROM gradle:8.11-jdk21-alpine AS build
WORKDIR /app

# Copia apenas os arquivos necessários para o build
COPY build.gradle settings.gradle ./
COPY gradlew ./gradlew
COPY src ./src

RUN gradle build -x test

# Etapa final, imagem mais leve
FROM amazoncorretto:21-alpine

WORKDIR /app

# Define timezone
RUN ln -snf /usr/share/zoneinfo/America/Sao_Paulo /etc/localtime && \
    echo America/Sao_Paulo > /etc/timezone

COPY --from=build /app/build/libs/*.jar /app/application.jar

CMD ["java", "-jar", "/app/application.jar"]
