
@echo off

echo Starting build processes.

set PATH_WITH_PROJECTS=D:\Intellij IDEA Projects

echo PATH_WITH_PROJECTS: '%PATH_WITH_PROJECTS%'

call mvn -f "%PATH_WITH_PROJECTS%\kafka-test\kafka-test\pom.xml" -T 1C -Dmaven.javadoc.skip=true -Dmaven.source.skip=true -Dcheckstyle.skip=true -Dpmd.skip=true -Dspotbugs.skip=true -DskipTests clean install

if %errorlevel% neq 0 (
    echo ERROR: Project 1 build failed!
    exit /b 1
)

call mvn -f "%PATH_WITH_PROJECTS%\ship-simulation\ship-simulation\pom.xml" -T 1C -Dmaven.javadoc.skip=true -Dmaven.source.skip=true -Dcheckstyle.skip=true -Dpmd.skip=true -Dspotbugs.skip=true -DskipTests clean install

if %errorlevel% neq 0 (
    echo ERROR: Project 2 build failed!
    exit /b 1
)

echo All maven builds completed!

timeout /t 3 /nobreak >nul

