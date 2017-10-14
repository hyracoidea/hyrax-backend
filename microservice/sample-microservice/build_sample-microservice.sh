#!/usr/bin/env bash

../mvnw clean install --non-recursive

if [ $? -ne 0 ]; then
    exit 1
fi

../mvnw -pl data clean install

if [ $? -ne 0 ]; then
    exit 1
fi

../mvnw -pl service clean install

if [ $? -ne 0 ]; then
    exit 1
fi

../mvnw -pl rest-api clean install docker:build

if [ $? -ne 0 ]; then
    exit 1
else
    exit 0
fi