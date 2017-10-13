#!/usr/bin/env bash

cd common-client
sh ./build_common-client.sh

if [ $? -ne 0 ]; then
    exit 1
fi

cd ../sample-client
sh ./build_sample-client.sh

if [ $? -ne 0 ]; then
    exit 1
else
    exit 0
fi