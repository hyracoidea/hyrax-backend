#!/usr/bin/env bash

cd ../
sh ./build_parent_module.sh
cd sample-client

../mvnw clean install