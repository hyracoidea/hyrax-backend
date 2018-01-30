#!/usr/bin/env bash

sh build_account_microservice.sh

if [ $? -ne 0 ]; then
    exit 1
fi

sh start_account_microservice.sh

if [ $? -ne 0 ]; then
    exit 1
fi

sh run_rest_api_test.sh

if [ $? -ne 0 ]; then
    sh shutdown_account_microservice.sh
    exit 1
else
    sh shutdown_account_microservice.sh
    exit 0
fi