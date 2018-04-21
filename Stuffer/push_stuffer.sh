#!/usr/bin/env bash

# Getting login command to stuffer repository
echo "[STUFFER UPDATE] Getting logging command to remote repository"
command="sudo $(~/.local/bin/aws ecr get-login --no-include-email --region eu-central-1)"
echo "[STUFFER UPDATE] Logging command is:"
echo "$command"

# Logging into repository
echo "[STUFFER UPDATE] Logging into remote repository"
eval "$command"
echo "[STUFFER UPDATE] Successfully logged into remote repository"

# Building *.jar file
echo "[STUFFER UPDATE] Building application"
mvn clean install
echo "[STUFFER UPDATE] Application was successfully built"

# Building docker image
echo "[STUFFER UPDATE] Building docker image from application"
sudo docker build -t stuffer_stuffer Stuffer
sudo docker tag stuffer_stuffer:latest 773539405688.dkr.ecr.eu-central-1.amazonaws.com/stuffer:latest
echo "[STUFFER UPDATE] Docker image was successfully built"

# Pushing image into repository
echo "[STUFFER UPDATE] Pushing docker image into the repository"
sudo docker push 773539405688.dkr.ecr.eu-central-1.amazonaws.com/stuffer:latest
echo "[STUFFER UPDATE] Docker image was successfully pushed into the repository"

# Updating application on server from repository
echo "[STUFFER UPDATE] Running commands on remote server..."

APP_PATH="~/stuffer/docker-compose.yml"
cmd[0]="eval sudo $command"
cmd[1]="sudo docker-compose -f $APP_PATH down"
cmd[2]="sudo docker-compose -f $APP_PATH pull stuffer"
cmd[3]="sudo docker-compose -f $APP_PATH up -d"

# running commands
for cmdi in "${cmd[@]}"
do
    ssh -i ~/ssh/aws_pk.pem ubuntu@ec2-18-194-201-221.eu-central-1.compute.amazonaws.com $cmdi
done

echo "[STUFFER UPDATE] Application update finished"
echo "[STUFFER UPDATE] Application is online on https://stuffer.harmady.com"
