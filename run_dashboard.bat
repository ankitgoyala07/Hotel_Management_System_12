@echo off
cd /d "%~dp0"
echo Compiling project files...
javac -d build/classes -cp "lib/AbsoluteLayout.jar;lib/jcalendar-1.4.jar;lib/mysql-connector-j-9.7.0.jar" -sourcepath src src/view/admindashboard.java src/view/roommanagement.java src/view/BookingManagement.java src/view/loginpage.java
if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b %errorlevel%
)
echo Launching Admin Dashboard...
start java -cp "build/classes;lib/AbsoluteLayout.jar;lib/jcalendar-1.4.jar;lib/mysql-connector-j-9.7.0.jar" view.admindashboard
exit
