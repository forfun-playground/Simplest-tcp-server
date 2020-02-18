@echo off
title UWNB Console
chcp 65001

echo Starting Node.
echo.

java -version:1.8 -Dfile.encoding=UTF-8 -Dlogback.configurationFile=conf/bothub-logback.xml -jar bin\bothub.jar

if ERRORLEVEL 1 goto error
goto end

:error
echo Node Terminated Abnormally!

:end
echo Node Terminated.
pause