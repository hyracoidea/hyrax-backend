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

cd ../../spring-boot-starter/scripts
sh build_parent_module.sh

if [ $? -ne 0 ]; then
    exit 1
fi

sh build_spring_boot_starters.sh

cd ../../microservice/scripts
sh build_parent_module.sh

if [ $? -ne 0 ]; then
    exit 1
fi

sh build_microservices_and_run_rest_api_test.sh

if [ $? -ne 0 ]; then
    docker stop hyrax_mysql&
    sleep 15
    exit 1
fi

exit 0