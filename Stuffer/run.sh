#!/usr/bin/env bash

docker build -t iwaneez/stuffer .
docker run -e spring.profiles.active="dev" -p 8080:8080 --name stuffer iwaneez/stuffer