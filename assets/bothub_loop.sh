#!/bin/bash
while :;
do
    java -version:1.8 -Dfile.encoding=UTF-8 -Dlogback.configurationFile=conf/bothub-logback.xml -jar bin/bothub.jar
    [ $? -ne 2 ] && break
    sleep 10;
done
