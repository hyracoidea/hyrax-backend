#!/usr/bin/env bash

callTheHealthCheckEndpoint() {
    actualHealthCheckEndpointResponse=$(curl http://localhost:8090/sample/health -s)
}

expectedHealthCheckEndpointResponse="{\"status\":\"UP\"}"

callTheHealthCheckEndpoint
while [ "$actualHealthCheckEndpointResponse" != "$expectedHealthCheckEndpointResponse" ]
    do
        sleep 5
        callTheHealthCheckEndpoint
    done

../mvnw -pl rest-api-test clean verify

if [ $? -eq 0 ]
then
    exit 0
else
    exit 1
fi