#!/usr/bin/env bash

cd ../sample-microservice
sh start_sample_microservice_and_run_rest_api_test.sh

if [ $? -ne 0 ]; then
    exit 1
else
    exit 0
fi

cd scripts