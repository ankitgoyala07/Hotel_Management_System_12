@echo off
echo ========================================================
echo        Running Hotel Management System JUnit Tests
echo ========================================================

REM Create build directories if they do not exist
if not exist "build\classes" mkdir "build\classes"
if not exist "build\test\classes" mkdir "build\test\classes"

echo [1/3] Compiling main source code (Java 25 target)...
javac --release 25 -cp "lib\*" -d build\classes src\model\*.java src\dao\*.java src\database\*.java src\view\*.java src\controller\*.java src\hotel_management\*.java
if %errorlevel% neq 0 (
    echo Error during source code compilation!
    exit /b %errorlevel%
)

echo [2/3] Compiling test suite (Java 25 target)...
javac --release 25 -cp "lib\*;build\classes" -d build\test\classes test\model\*.java
if %errorlevel% neq 0 (
    echo Error during test compilation!
    exit /b %errorlevel%
)

echo [3/3] Executing JUnit tests...
java -jar lib\junit-platform-console-standalone-1.10.0.jar --class-path "build\classes;build\test\classes;lib\mysql-connector-j-8.4.0.jar;lib\jcalendar-1.4.jar;lib\AbsoluteLayout.jar" --scan-classpath --include-classname=.*TableTest.*

echo ========================================================
echo Test execution complete.
echo ========================================================
