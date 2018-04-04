FROM java:8

VOLUME /tmp
ADD project-rest-api-1.0-SNAPSHOT.jar app.jar
ADD wait-for-it.sh wait-for-it.sh
ADD start.sh start.sh

RUN chmod +x wait-for-it.sh
RUN chmod +x start.sh

EXPOSE 8080

RUN sh -c 'touch /app.jar'
CMD ["sh", "start.sh"]