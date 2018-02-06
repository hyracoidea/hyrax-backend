#!/usr/bin/env bash

cd ../rest-api
docker-compose up&
cd ../

if [ $? -ne 0 ]; then
    exit 1
else
    exit 0
fi

cd scripts