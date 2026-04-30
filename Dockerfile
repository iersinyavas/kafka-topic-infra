# Uygulamanın çaliması için JDK lazım.
# FROM amazoncorretto:17
FROM eclipse-temurin:21-jdk

# Projemizin Jar dosyasının konumu
ARG JAR_FILE=target/*.jar

#  Projenin Jar halini Docker'ın içine şu isimle kopayala
COPY ${JAR_FILE} kafka-topic-infra.jar

# terminalden çalıştırmak istediğin komutları CMD ile kullaniyorsunuz.
CMD apt-get update
CMD apt-get upgrade -y

# iç portu sabitlemek için 
EXPOSE 9700

# Uygulamanın çalışacağı komut
ENTRYPOINT ["java","-jar","kafka-topic-infra.jar"]
