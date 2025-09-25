
@echo off
echo =============================
echo Maven Version Updater
echo =============================
set /p BASE_DIR="Enter root directory [Leave it empty for the default directory: 'D:\Intellij IDEA Projects']: "
if "!BASE_DIR!"=="" set BASE_DIR=D:\Intellij IDEA Projects

echo Root directory is set to: '%BASE_DIR%'

call mvn -f "%BASE_DIR%\kafka-test\kafka-test\pom.xml" -T 1C -Dmaven.javadoc.skip=true -Dmaven.source.skip=true -Dcheckstyle.skip=true -Dpmd.skip=true -Dspotbugs.skip=true -DskipTests clean install

if %errorlevel% neq 0 (
    echo ERROR: 'kafka-test' 1 build failed!
    exit /b 1
)

call mvn -f "%BASE_DIR%\ship-simulation\ship-simulation\pom.xml" -T 1C -Dmaven.javadoc.skip=true -Dmaven.source.skip=true -Dcheckstyle.skip=true -Dpmd.skip=true -Dspotbugs.skip=true -DskipTests clean install

if %errorlevel% neq 0 (
    echo ERROR: 'ship-simulation' 2 build failed!
    exit /b 1
)

echo All version updates completed!
echo =============================
timeout /t 3 /nobreak >nul

