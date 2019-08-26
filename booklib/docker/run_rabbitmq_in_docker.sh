#!/bin/sh
docker run --rm -d -p 5671:5671 -p 5672:5672 --hostname orsapps-tst-rabbit --name orsapps-tst-rabbit rabbitmq:latest
