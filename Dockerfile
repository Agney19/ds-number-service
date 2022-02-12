FROM openjdk:11

COPY build/libs/ds-number-service.jar /ds-number-service.jar

CMD java -Dspring.datasource.url=$DB_URL -Dspring.datasource.username=$DB_USER -Dspring.datasource.password=$DB_PASSWORD -Dnum-service.max-value=$SERVICE_MAX_NUMBER -jar /ds-number-service.jar
