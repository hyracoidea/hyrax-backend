#!/usr/bin/env bash

sh ./build_parent_module.sh

cd common-client
sh ./build_common-client.sh
cd ../

cd sample-client
sh ./build_sample-client.sh
cd ../