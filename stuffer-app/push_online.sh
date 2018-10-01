#!/usr/bin/env bash

# Variables
cmdtag="[STUFFER UPDATE]"
remote="ssh -i ~/ssh/aws_pk.pem ubuntu@ec2-18-194-201-221.eu-central-1.compute.amazonaws.com"

# Building *.jar file
echo "$cmdtag Building application"
mvn clean install
echo "$cmdtag Application was successfully built"

# Building docker image
echo "$cmdtag Building docker image from application"
sudo docker build -t stuffer_stuffer stuffer-app
sudo docker tag stuffer_stuffer:latest 773539405688.dkr.ecr.eu-central-1.amazonaws.com/stuffer:latest
echo "$cmdtag Docker image was successfully built"

# Pushing image into repository
echo "$cmdtag Pushing docker image into the repository"
sudo docker push 773539405688.dkr.ecr.eu-central-1.amazonaws.com/stuffer:latest
echo "$cmdtag Docker image was successfully pushed into the repository"

# Getting login command to stuffer repository
echo "$cmdtag Getting logging command to remote repository"
command="sudo $(~/.local/bin/aws ecr get-login --no-include-email --region eu-central-1)"
echo "$cmdtag Logging command is:"
echo "$command"

# Logging into repository
echo "$cmdtag Logging into remote repository"
eval "${command}"
echo "$cmdtag Successfully logged into remote repository"

# Updating application on server from repository
echo "$cmdtag Running commands on remote server..."
APP_PATH="~/stuffer/docker-compose.yml"
cmd[0]="eval $command"
cmd[1]="sudo docker-compose -f $APP_PATH down"
cmd[2]="sudo docker-compose -f $APP_PATH pull stuffer"
cmd[3]="sudo docker-compose -f $APP_PATH up -d"

# running commands
for cmdi in "${cmd[@]}"
do
    ${remote} ${cmdi}
done
echo "$cmdtag Application update finished"

# removing unused images
echo "$cmdtag Removing unused images"
${remote} sudo docker image prune -f
echo "$cmdtag Clean-up finished"

echo "$cmdtag Application is online on https://stuffer.harmady.com"
