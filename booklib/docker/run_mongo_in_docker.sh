#!/bin/sh
docker run --name mongoindocker -d -p 27017:27017 -v /appdata/dockerData/dockerAppData/mongodb/data:/data/db mongo:latest
