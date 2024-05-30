# Etapa de construcción
FROM maven:3.8.4-openjdk-17-slim AS builder

WORKDIR /app

# Copiar los archivos de configuración y de proyecto
COPY pom.xml .
COPY src ./src

# Instalar dependencias y construir el proyecto     
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM openjdk:17-jdk-slim

WORKDIR /app

# Establecer variables de entorno necesarias
ENV DB_HOST=mysql
ENV DB_PORT=3306
ENV DB_NAME=hydrometrics
ENV DB_USER=root
ENV DB_PASSWORD=root
ENV PORT=8080
ENV DATA_PROCESSOR_HOST=dataprocessor
ENV DATA_PROCESSOR_PROTOCOL=http
ENV DATA_PROCESSOR_PORT=8081

# Copiar el jar construido desde la etapa de construcción
COPY --from=builder /app/target/*.jar app.jar

# COPY --from=builder /app/src/main/resources/JSON/datos_unificados_short.json /app/src/main/resources/JSON/datos_unificados_short.json
# Exponer el puerto de la aplicación
EXPOSE $PORT
EXPOSE $PORT_SMTP

# Comando de entrada para ejecutar la aplicación
ENTRYPOINT ["sh", "-c", "java -jar app.jar"]