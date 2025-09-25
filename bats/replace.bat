:: Runs the PowerShell script provided with the directory.

:: Inputs must be as follows.

:: The PowerShell script to be run is provided with the array and is the 1st parameter. Example -> "C:\Users\Melih-PC\Desktop\auto\replace.ps1"
:: The jar/ear file whose text file will be updated is provided with its directory and is the 2nd parameter. Example -> "D:\Intellij IDEA Projects\leetcode-1\leetcode-1\target\leetcode-1-1.0-SNAPSHOT.jar"
:: The text file to be updated is provided with its directory and is the 3rd parameter. Example -> "config/test.properties"
:: The text to be replaced is the 4th parameter. Example -> "OldText"
:: The text to be replaced is the 5th parameter. Example -> "NewText""

@echo off
PowerShell.exe -ExecutionPolicy Bypass -File "C:\Users\Melih-PC\Desktop\auto\replace.ps1" "D:\Intellij IDEA Projects\leetcode-1\leetcode-1\target\leetcode-1-1.0-SNAPSHOT.jar" "config/test.properties" "OldText" "NewText"