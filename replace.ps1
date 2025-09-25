# PowerShell script to replace "OldText" with "NewText" in a file within a JAR archive

param(
    [Parameter(Mandatory=$true)]
    [string]$JarFilePath,
    
    [Parameter(Mandatory=$true)]
    [string]$TargetFileInJar,
    
    [Parameter(Mandatory=$true)]
    [string]$OldText,
    
    [Parameter(Mandatory=$true)]
    [string]$NewText,
    
    [string]$BackupExtension = ".bak"
)

# Function to display error and exit
function Exit-WithError {
    param([string]$Message)
    Write-Error $Message
    exit 1
}

# Check if Java is installed and available in PATH
try {
    $null = Get-Command "jar" -ErrorAction Stop
} catch {
    try {
        $null = Get-Command "java" -ErrorAction Stop
    } catch {
        Exit-WithError "Java is not installed or not in PATH. Please install Java to use this script."
    }
}

# Check if the JAR file exists
if (-not (Test-Path $JarFilePath)) {
    Exit-WithError "JAR file not found: $JarFilePath"
}

# Create a temporary directory for extraction
$tempDir = Join-Path $env:TEMP ([System.Guid]::NewGuid().ToString())
New-Item -ItemType Directory -Path $tempDir -Force | Out-Null

try {
    Write-Host "Extracting JAR file to temporary directory..." -ForegroundColor Yellow
    
    # Extract the specific file from JAR using jar command
    $jarCommand = "jar"
    $arguments = @("xf", "`"$JarFilePath`"", "`"$TargetFileInJar`"")
    
    # Change to temp directory and extract
    Push-Location $tempDir
    $process = Start-Process -FilePath $jarCommand -ArgumentList $arguments -Wait -PassThru -NoNewWindow
    Pop-Location
    
    if ($process.ExitCode -ne 0) {
        Write-Host "Trying alternative extraction method..." -ForegroundColor Yellow
        # Alternative method using java -jar for extraction
        Push-Location (Split-Path $JarFilePath)
        $process = Start-Process -FilePath "java" -ArgumentList @("-jar", "`"$JarFilePath`"", "--extract", "`"$TargetFileInJar`"", "`"$tempDir`"") -Wait -PassThru -NoNewWindow
        Pop-Location
        
        if ($process.ExitCode -ne 0) {
            Exit-WithError "Failed to extract file from JAR. Make sure the file path '$TargetFileInJar' is correct within the JAR."
        }
    }
    
    # Check if the extracted file exists
    $extractedFilePath = Join-Path $tempDir $TargetFileInJar
    if (-not (Test-Path $extractedFilePath)) {
        # Try to find the file with different path separators
        $alternativePath = $extractedFilePath -replace "/", "\"
        if (Test-Path $alternativePath) {
            $extractedFilePath = $alternativePath
        } else {
            Exit-WithError "Target file '$TargetFileInJar' was not found in the JAR archive."
        }
    }
    
    # Create backup of the original file
    $backupPath = "$extractedFilePath$BackupExtension"
    Copy-Item -Path $extractedFilePath -Destination $backupPath -Force
    
    Write-Host "Replacing '$OldText' with '$NewText' in the file..." -ForegroundColor Yellow
    
    # Read the file content and perform replacement
    $content = Get-Content -Path $extractedFilePath -Raw
    $newContent = $content -replace $OldText, $NewText
    
    # Write the modified content back to the file
    Set-Content -Path $extractedFilePath -Value $newContent -NoNewline
    
    Write-Host "Updating JAR file with modified content..." -ForegroundColor Yellow
    
    # Update the JAR file with the modified file
    Push-Location $tempDir
    $updateProcess = Start-Process -FilePath "jar" -ArgumentList @("uf", "`"$JarFilePath`"", "`"$TargetFileInJar`"") -Wait -PassThru -NoNewWindow
    Pop-Location
    
    if ($updateProcess.ExitCode -ne 0) {
        # Alternative update method
        Push-Location $tempDir
        $updateProcess = Start-Process -FilePath "java" -ArgumentList @("-jar", "`"$JarFilePath`"", "--update", "`"$TargetFileInJar`"") -Wait -PassThru -NoNewWindow
        Pop-Location
        
        if ($updateProcess.ExitCode -ne 0) {
            Exit-WithError "Failed to update JAR file with modified content."
        }
    }
    
    Write-Host "Successfully replaced '$OldText' with '$NewText' in '$TargetFileInJar'" -ForegroundColor Green
    Write-Host "JAR file updated: $JarFilePath" -ForegroundColor Green
    
} finally {
    # Clean up temporary directory
    if (Test-Path $tempDir) {
        Remove-Item -Path $tempDir -Recurse -Force -ErrorAction SilentlyContinue
    }
}