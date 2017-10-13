#!/usr/bin/env bash

../mvnw -pl data clean install

if [ $? -ne 0 ]; then
    exit 1
fi

../mvnw -pl service clean install

if [ $? -ne 0 ]; then
    exit 1
fi

../mvnw -pl rest-api clean package docker:build

if [ $? -ne 0 ]; then
    exit 1
else
    exit 0
fi