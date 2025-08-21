# Usa un'immagine di base Java
FROM adoptopenjdk:11-jdk-hotspot

# Copia il file JAR del progetto TelegramBot nella directory /app all'interno del contenitore
COPY target/TelegramBot-1.0.0.jar /app/TelegramBot-1.0.0.jar

# Comando per avviare l'applicazione
CMD ["java","-Djasypt.encryptor.password=<<PASSWORD_PLACEHOLDER>>","-Dspring.profiles.active=<<PROFILE_PLACEHOLDER>>","-jar","/app/TelegramBot-1.0.0.jar"]