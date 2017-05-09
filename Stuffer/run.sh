#!/usr/bin/env bash

docker build -t iwaneez/stuffer .

docker run --name postgreDB -p 5432:5432 -e POSTGRES_DB=stuffer -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=root123 -d postgres:9.6.2
docker run --name stuffer -p 8080:8080 -e spring.profiles.active=dev --link postgreDB:postgre iwaneez/stuffer