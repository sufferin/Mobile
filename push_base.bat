@echo off
echo Preparing Room project...

:: Create Room directory if it doesn't exist
if not exist "Room" mkdir "Room"

:: Move files into Room directory (excluding git, idea and the script itself)
echo Moving files to Room folder...
robocopy . Room /E /MOVE /XF push_base.bat /XD Room .git .idea .kotlin > nul

:: Git operations
echo.
echo Initializing Git commit...
git add .
git commit -m "Room task implementation"

:: Reset remote to force browser login if token fails
echo.
echo Setting remote to https://github.com/sufferin/Mobile.git
git remote set-url origin https://github.com/sufferin/Mobile.git

echo.
echo Pushing to GitHub...
echo If a window pops up, please log in to your GitHub account.
git push -u origin main

if %errorlevel% neq 0 (
    echo.
    echo [ERROR] Push failed. Trying to clear old credentials...
    git credential-manager reject https://github.com
    echo.
    echo Retrying push...
    git push -u origin main
)

echo.
echo Done! Press any key to close.
pause