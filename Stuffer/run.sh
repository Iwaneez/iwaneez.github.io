#!/usr/bin/env bash

sudo docker build -t stuffer .
sudo docker run --name stufferCnt stuffer -p 8080:8080