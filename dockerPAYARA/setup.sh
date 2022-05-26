#!/bin/bash

rm stw22.war
cp ../stw22/target/stw22-1.0-SNAPSHOT.war .
mv stw22-1.0-SNAPSHOT.war stw22.war 

docker build -t stw22 .
docker run --rm --name stw22 -p 8282:8080 --mount type=bind,source=$(pwd)/bd,target=/opt/payara/db stw22 


