#!/usr/bin/env bash

cd ../

./mvnw clean install --non-recursive

if [ $? -ne 0 ]; then
    exit 1
else
    exit 0
fi

cd scripts