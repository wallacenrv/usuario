# Etapa de construção (Build Phase)
# Usa a imagem base do Gradle com o JDK 17 para construir o projeto
FROM gradle:7.5-jdk17 AS build

# Define o diretório de trabalho dentro do container como /app
WORKDIR /app

# Copia o conteúdo do diretório local para o container no diretório /app
COPY . .

# Executa o comando Gradle para compilar o projeto. A flag --no-daemon faz com que o Gradle execute sem rodar o daemon em segundo plano, garantindo que o processo seja isolado.
RUN gradle build --no-daemon

# imagem base correta (Java 17)
FROM eclipse-temurin:17-jre-jammy
# define diretório de trabalho dentro do container
WORKDIR /app

# copia o jar gerado para dentro do container (ajustei o caminho e nome)
COPY build/libs/usuario-0.0.1-SNAPSHOT.jar /app/usuario.jar

# expõe a porta que a aplicação usa
EXPOSE 8080

# comando para rodar o jar
CMD ["java", "-jar", "/app/usuario.jar"]
