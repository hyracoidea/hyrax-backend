#!/usr/bin/env bash

cd ../sample-microservice/scripts
sh build_sample_microservice_and_run_rest_api_test.sh

if [ $? -ne 0 ]; then
    exit 1
fi

cd ../../scripts

cd ../account-microservice/scripts
sh build_account_microservice_and_run_rest_api_test.sh

if [ $? -ne 0 ]; then
    exit 1
fi

cd ../../scripts

cd ../project-microservice/scripts
sh build_project_microservice_and_run_rest_api_test.sh

if [ $? -ne 0 ]; then
    exit 1
fi

cd ../../scripts

cd ../email-microservice/scripts
sh build_email_microservice_and_run_rest_api_test.sh

if [ $? -ne 0 ]; then
    exit 1
fi

cd ../../scripts