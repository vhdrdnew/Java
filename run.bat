@echo off
chcp 65001 >nul
if not exist out mkdir out
javac -encoding UTF-8 -d out src\app\*.java src\model\*.java src\exceptions\*.java src\strategy\*.java src\observer\*.java src\storage\*.java src\service\*.java
if errorlevel 1 exit /b 1
java -Dfile.encoding=UTF-8 -Dstdout.encoding=UTF-8 -cp out app.Main
