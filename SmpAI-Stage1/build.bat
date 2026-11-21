@echo off
where gradle >nul 2>nul
if errorlevel 1 (
  echo Gradle was not found. Install Gradle 9.5 or open this folder in IntelliJ IDEA with Gradle support.
  pause
  exit /b 1
)
gradle build
pause
