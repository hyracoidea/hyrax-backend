#!/usr/bin/env bash

cd ../common-client/scripts
sh build_common_client.sh

if [ $? -ne 0 ]; then
    exit 1
fi

cd ../../sample-client/scripts
sh build_sample_client.sh

if [ $? -ne 0 ]; then
    exit 1
else
    exit 0
fi

cd scripts