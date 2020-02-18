#!/bin/bash
while :;
do
    java -Dfile.encoding=UTF-8 -Dlogback.configurationFile=conf/node-logback.xml -jar bin/node.jar
    [ $? -ne 2 ] && break
    sleep 10;
done
