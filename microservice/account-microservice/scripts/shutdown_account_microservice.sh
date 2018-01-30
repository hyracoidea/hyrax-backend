#!/usr/bin/env bash

docker stop account_microservice&
docker stop hyrax_mysql&

sleep 15

exit 0