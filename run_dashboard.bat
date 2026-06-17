@echo off
cd /d "%~dp0"
echo Compiling project files...
javac -d build/classes -cp "lib/AbsoluteLayout.jar;lib/jcalendar-1.4.jar;lib/mysql-connector-j-8.4.0.jar" -sourcepath src src/hotel_management/Hotel_Management.java
if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b %errorlevel%
)
echo Launching Application...
start java -cp "build/classes;lib/AbsoluteLayout.jar;lib/jcalendar-1.4.jar;lib/mysql-connector-j-8.4.0.jar" hotel_management.Hotel_Management
exit
