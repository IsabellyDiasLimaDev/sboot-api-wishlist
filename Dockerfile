# syntax=docker/dockerfile:1
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

# Copia apenas arquivos de definição de projeto primeiro (cache de dependências)
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

RUN chmod +x mvnw
RUN ./mvnw -q -B dependency:go-offline

# Copia código fonte e empacota
COPY src src
RUN ./mvnw -q -B clean package -DskipTests

# Imagem final (runtime)
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copia o JAR gerado
COPY --from=builder /app/target/sboot-api-wishlist-0.0.1-SNAPSHOT.jar app.jar

# Porta padrão do Spring Boot (ajuste se necessário)
EXPOSE 8080


ENTRYPOINT ["java","-jar","app.jar"]