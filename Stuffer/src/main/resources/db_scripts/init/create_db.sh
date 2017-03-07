#!/usr/bin/env bash
sudo -u postgres psql
\password postgres # using root123
CREATE DATABASE stuffer;