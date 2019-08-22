#!/bin/sh

# to view application output in console, run it without -d switch:
#docker run --name booklib -p 8080:8080 booklib:0.1.0

docker run --rm --name booklib -d -p 8080:8080 booklib:0.1.0

