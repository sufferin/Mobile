@echo off
echo [1/3] Clearing old Git credentials...
:: Command to clear Windows Credential Manager for GitHub
echo url=https://github.com | git credential-manager erase

echo.
echo [2/3] Preparing files...
:: Create Room directory if it doesn't exist
if not exist "Room" mkdir "Room"

:: Move files (robocopy is more reliable for moving folders)
robocopy . Room /E /MOVE /XF push_base.bat /XD Room .git .idea .kotlin > nul

:: Git operations
git add .
git commit -m "Room task update"

echo.
echo [3/3] Pushing to GitHub...
:: Use clean URL to trigger browser login
git remote set-url origin https://github.com/sufferin/Mobile.git

echo.
echo IMPORTANT: When the GitHub login window appears, select "Sign in with your browser".
git push -u origin main

if %errorlevel% neq 0 (
    echo.
    echo Pushing failed. If you still see 403 error, please create a NEW token on GitHub
    echo with 'repo' scope and use: git remote set-url origin https://TOKEN@github.com/sufferin/Mobile.git
)

echo.
echo Done! Press any key to close.
pause