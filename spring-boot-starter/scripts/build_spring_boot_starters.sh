#!/usr/bin/env bash

cd ../authentication/scripts
sh build_spring_boot_starter_authentication.sh

if [ $? -ne 0 ]; then
    exit 1
else
    exit 0
fi

cd scripts