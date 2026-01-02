@echo off
java -jar TaskManager.jar
if %errorlevel% neq 0 (
    echo.
    echo Application exited with error.
    pause
)
