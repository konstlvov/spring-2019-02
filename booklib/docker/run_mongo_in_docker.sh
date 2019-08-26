#!/bin/sh
docker run --rm --name mongoindocker -d -p 27017:27017 -v /appdata/dockerData/dockerAppData/mongodb/data:/data/db mongo:latest
