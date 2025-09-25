@echo off
setlocal enabledelayedexpansion
echo =============================
echo Git Recursive Project Updater
echo =============================

set /p BASE_DIR="Enter root directory to scan [Leave it empty for the default directory: 'D:\Intellij IDEA Projects']: "
if "!BASE_DIR!"=="" set BASE_DIR=D:\Intellij IDEA Projects

set /p BRANCH_NAME="Enter branch name [Leave it empty for the default branch: 'main']: "
if "!BRANCH_NAME!"=="" set BRANCH_NAME=main

if not exist "!BASE_DIR!\" (
    echo ERROR: Directory !BASE_DIR! does not exist!
    pause
    exit /b 1
)

echo.
echo Scanning for Git repositories (recursively)...
echo This may take a while for large directories...
echo.

REM First, count how many Git repos we have
set /a REPO_COUNT=0
for /f "delims=" %%i in ('dir "!BASE_DIR!\.git" /s /b /a:d 2^>nul') do (
    set /a REPO_COUNT+=1
)

if !REPO_COUNT! equ 0 (
    echo No Git repositories found in !BASE_DIR! or its subdirectories.
    pause
    exit /b 1
)

echo Found !REPO_COUNT! Git repositories.
echo.
set /p CONFIRM="Proceed with updating all repositories? [Y/n]: "
if /i not "!CONFIRM!"=="y" if /i not "!CONFIRM!"=="" (
    echo Operation cancelled.
    pause
    exit /b 0
)

echo.
echo Starting recursive update...
echo ============================

set /a CURRENT=0
set /a SUCCESS=0
set /a FAIL=0

for /f "delims=" %%i in ('dir "!BASE_DIR!\.git" /s /b /a:d 2^>nul') do (
    set "PROJECT_DIR=%%i\.."
    set /a CURRENT+=1
    
    echo.
    echo [!CURRENT! / !REPO_COUNT!] !PROJECT_DIR!
    echo ----------------------------------------
    
    cd /d "!PROJECT_DIR!"
    
    git fetch origin
    if !errorlevel! equ 0 (
        git pull origin !BRANCH_NAME!
        if !errorlevel! equ 0 (
            echo Updated successfully
            set /a SUCCESS+=1
        ) else (
            echo Pull failed
            set /a FAIL+=1
        )
    ) else (
        echo Fetch failed
        set /a FAIL+=1
    )
)

echo.
echo ============================
echo FINAL RESULTS:
echo Total processed: !CURRENT!
echo Successful: !SUCCESS!
echo Failed: !FAIL!
echo =============================
timeout /t 3 /nobreak >nul