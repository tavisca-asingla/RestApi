FROM adoptopenjdk/openjdk10
COPY target/springbootdemo-0.0.1-SNAPSHOT.jar /var/www/RestApi/

WORKDIR /var/www/RestApi/
EXPOSE 8090
RUN chmod 777 springbootdemo-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","springbootdemo-0.0.1-SNAPSHOT.jar"]