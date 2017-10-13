#!/usr/bin/env bash

cd sample-microservice
sh ./start_sample-microservice.sh

if [ $? -ne 0 ]; then
    exit 1
else
    exit 0
fi