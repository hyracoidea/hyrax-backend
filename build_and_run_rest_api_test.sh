#!/usr/bin/env bash

cd client/scripts
sh build_parent_module.sh

if [ $? -ne 0 ]; then
    exit 1
fi

sh build_clients.sh

if [ $? -ne 0 ]; then
    exit 1
fi

cd ../../microservice/scripts
sh build_parent_module.sh

if [ $? -ne 0 ]; then
    exit 1
fi

sh build_microservices_and_run_rest_api_test.sh

if [ $? -ne 0 ]; then
    exit 1
else
    exit 0
fi

cd ../