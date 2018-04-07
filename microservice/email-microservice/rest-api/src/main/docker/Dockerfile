FROM java:8

VOLUME /tmp
ADD email-rest-api-1.0-SNAPSHOT.jar app.jar
ADD start.sh start.sh

RUN chmod +x start.sh

EXPOSE 8083

RUN sh -c 'touch /app.jar'
CMD ["sh", "start.sh"]