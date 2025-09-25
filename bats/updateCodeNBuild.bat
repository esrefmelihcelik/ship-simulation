@echo off
setlocal enabledelayedexpansion

echo Starting batch file execution sequence...
echo.

:: List of batch files to execute (modify this list as needed)
set "batch_files=multipleFetchNPull.bat update-versions.bat clean-install.bat"

:: Counter for tracking progress
set count=0

:: Loop through each batch file
for %%f in (%batch_files%) do (
    set /a count+=1
    echo [%count%] Executing: %%f
    
    :: Execute the batch file and check errorlevel
    call "%%f"
    
    :: Check if the batch file executed successfully
    if errorlevel 1 (
        echo.
        echo ERROR: %%f failed with error code !errorlevel!
        echo Execution stopped.
        goto :error
    )
    
    echo [%count%] Completed: %%f
    echo.
)

echo All batch files executed successfully!
goto :end

:error
echo.
echo Batch execution sequence failed at step %count%
exit /b 1

:end
echo.
echo Batch execution sequence completed successfully!
exit /b 0