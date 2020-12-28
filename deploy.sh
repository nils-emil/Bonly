#!/usr/bin/env bash
ssh -i bonly.pem ec2-user@bonly.ee rm *.jar
scp -i bonly.pem -r ./build/libs/*.jar ec2-user@bonly.ee:/home/ec2-user/
ssh -i bonly.pem ec2-user@bonly.ee sh deploy.sh
$SHELL
