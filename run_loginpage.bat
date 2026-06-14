@echo off
cd /d "%~dp0"
echo Cleaning build directory...
if exist build\classes rmdir /s /q build\classes
mkdir build\classes
echo Compiling project files...
dir /s /b src\*.java > sources.txt
javac -d build/classes -cp "lib/AbsoluteLayout.jar;lib/jcalendar-1.4.jar;lib/mysql-connector-j-8.4.0.jar" @sources.txt
del sources.txt
if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b %errorlevel%
)
echo Launching Login Page...
start java -cp "build/classes;lib/AbsoluteLayout.jar;lib/jcalendar-1.4.jar;lib/mysql-connector-j-8.4.0.jar" hotel_management.Hotel_Management
exit
