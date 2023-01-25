FROM adoptopenjdk:11-jre-hotspot
WORKDIR /app
COPY build/libs/url-shortener*.jar url.jar
EXPOSE 9000
USER 1001
ENTRYPOINT ["java","-jar","url.jar"]