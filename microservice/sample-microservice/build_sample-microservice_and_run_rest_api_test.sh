#!/usr/bin/env bash

sh ./build_sample-microservice.sh

sh ./start_sample-microservice.sh
sh ./run_rest_api_test.sh

sh ./shutdown_sample-microservice.sh