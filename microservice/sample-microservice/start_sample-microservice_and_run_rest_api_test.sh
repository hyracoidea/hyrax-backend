#!/usr/bin/env bash

sh ./start_sample-microservice.sh

if [ $? -ne 0 ]; then
    exit 1
fi

sh ./run_rest_api_test.sh

if [ $? -ne 0 ]; then
    exit 1
else
    exit 0
fi