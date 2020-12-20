#!/usr/bin/env bash
ssh -i bonly.pem ec2-user@bonly.ee rm *.jar
scp -i bonly.pem -r ./build/libs/*.jar ec2-user@bonly.ee:/home/ec2-user/
#ssh -i bonly.pem ec2-user@bonly.ee sudo kill $(ps aux | grep "java" | grep -v 'grep' | awk '{print $2}')
#ssh -i bonly.pem ec2-user@bonly.ee sudo nohup java -jar bonly-0.0.1-SNAPSHOT.jar > bonly.log 2>&1 &
$SHELL
