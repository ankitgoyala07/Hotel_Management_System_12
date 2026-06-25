# PowerShell Test Runner for JUnit Tests
Write-Host "========================================================" -ForegroundColor Cyan
Write-Host "       Running Hotel Management System JUnit Tests" -ForegroundColor Cyan
Write-Host "========================================================" -ForegroundColor Cyan

New-Item -ItemType Directory -Force -Path "build\classes", "build\test\classes" | Out-Null

Write-Host "[1/3] Compiling main source code..." -ForegroundColor Yellow
$srcFiles = Get-ChildItem -Recurse -Filter *.java src | Select-Object -ExpandProperty FullName
javac -cp "lib\*" -d build\classes $srcFiles
if ($LASTEXITCODE -ne 0) {
    Write-Host "Error during source code compilation!" -ForegroundColor Red
    exit $LASTEXITCODE
}

Write-Host "[2/3] Compiling test suite..." -ForegroundColor Yellow
$testFiles = Get-ChildItem -Recurse -Filter *.java test | Select-Object -ExpandProperty FullName
javac -cp "lib\*;build\classes" -d build\test\classes $testFiles
if ($LASTEXITCODE -ne 0) {
    Write-Host "Error during test compilation!" -ForegroundColor Red
    exit $LASTEXITCODE
}

Write-Host "[3/3] Executing JUnit 5 tests..." -ForegroundColor Green
java -jar lib\junit-platform-console-standalone-1.10.0.jar --class-path "build\classes;build\test\classes;lib\mysql-connector-j-8.4.0.jar;lib\jcalendar-1.4.jar;lib\AbsoluteLayout.jar" --scan-classpath --include-classname=.*Test.*

Write-Host "========================================================" -ForegroundColor Cyan
Write-Host "Test execution complete." -ForegroundColor Cyan
Write-Host "========================================================" -ForegroundColor Cyan
