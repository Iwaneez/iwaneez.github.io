#!/usr/bin/env bash
### running using plain docker ###
#
#docker build -t iwaneez/stuffer .
#
#docker run --name postgreDB -d -p 5432:5432 -e POSTGRES_DB=stuffer -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=root123 -v /home/iwaneez/postgreData:/var/lib/postgresql/data postgres:9.6.2
#docker run --name stuffer -p 8080:8080 -e spring.profiles.active=dev --link postgreDB:postgre iwaneez/stuffer

mvn clean package
#sudo docker-compose build
sudo docker-compose down
sudo docker-compose up --build