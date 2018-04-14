#!/usr/bin/env bash

sudo docker run -it --rm \
      -v /home/ubuntu/nginx/certs:/etc/letsencrypt \
      -v /home/ubuntu/nginx/certs-data:/data/letsencrypt \
      certbot/certbot \
      certonly --webroot \
      --register-unsafely-without-email --agree-tos \
      --webroot-path=/data/letsencrypt \
      -d harmady.com -d www.harmady.com \
      -d stuffer.harmady.com -d www.stuffer.harmady.com \
      -d blog.harmady.com -d www.blog.harmady.com