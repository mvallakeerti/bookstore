#!/usr/bin/env bash
echo 'Starting my app'
cd '/home/ec2-user/'
# Run the JAR file
nohup java -jar jpacrud-0.0.1-SNAPSHOT.jar > application.log 2>&1 &