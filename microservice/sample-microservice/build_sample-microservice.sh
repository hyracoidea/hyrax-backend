#!/usr/bin/env bash

cd ../
sh ./build_parent_module.sh
cd sample-microservice

../mvnw -pl '!rest-api, !rest-api-test' clean install
../mvnw -pl rest-api clean package docker:build