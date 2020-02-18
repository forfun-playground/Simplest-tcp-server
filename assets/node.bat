@echo off
title UWNN Console
chcp 65001

echo Starting Node.
echo.

java -version:1.8 -Dfile.encoding=UTF-8 -Dlogback.configurationFile=conf/node-logback.xml -jar bin\node.jar

if ERRORLEVEL 1 goto error
goto end

:error
echo Node Terminated Abnormally!

:end
echo Node Terminated.
pause