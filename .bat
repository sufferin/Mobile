@echo off
:: Устанавливаем кодировку UTF-8
chcp 65001 >nul
setlocal enabledelayedexpansion

set LOG_FILE=%~dp0git_log.txt
echo --- START LOG %DATE% %TIME% --- > "%LOG_FILE%"

:: Конфигурация
set REPO_URL=https://github.com/sufferin/Mobile.git
set SOURCE_DIR=%~dp0
set SOURCE_DIR=%SOURCE_DIR:"=%
:: Клонируем прямо в текущую папку для диагностики
set TEMP_CLONE_DIR=%SOURCE_DIR%temp_push_repo
set TARGET_SUBFOLDER=StudentTask

if exist "%TEMP_CLONE_DIR%" (
    echo [0/5] Clearing old temp dir... >> "%LOG_FILE%"
    rd /s /q "%TEMP_CLONE_DIR%" >> "%LOG_FILE%" 2>&1
)

echo [1/5] Проверка Git...
echo [1/5] Checking Git... >> "%LOG_FILE%"
git --version >> "%LOG_FILE%" 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Git not found >> "%LOG_FILE%"
    goto :FAIL
)

echo [2/5] Клонирование...
echo [2/5] Cloning... >> "%LOG_FILE%"
git clone %REPO_URL% "%TEMP_CLONE_DIR%" >> "%LOG_FILE%" 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Clone failed >> "%LOG_FILE%"
    goto :FAIL
)

echo [3/5] Подготовка папки...
echo [3/5] Preparing subfolder... >> "%LOG_FILE%"
if not exist "%TEMP_CLONE_DIR%\%TARGET_SUBFOLDER%" mkdir "%TEMP_CLONE_DIR%\%TARGET_SUBFOLDER%" >> "%LOG_FILE%" 2>&1

echo [4/5] Копирование...
echo [4/5] Copying files... >> "%LOG_FILE%"
:: Копируем, исключая служебные файлы и саму временную папку
robocopy "%SOURCE_DIR%." "%TEMP_CLONE_DIR%\%TARGET_SUBFOLDER%" /E /XD .git .gradle .kotlin build .idea temp_push_repo /XF push_student_task.bat test_write.txt local.properties git_log.txt >> "%LOG_FILE%" 2>&1

echo [5/5] Push...
echo [5/5] Pushing... >> "%LOG_FILE%"
cd /d "%TEMP_CLONE_DIR%"
git add . >> "%LOG_FILE%" 2>&1
git commit -m "Add StudentTask folder" >> "%LOG_FILE%" 2>&1
:: Пробуем пуш
git push origin main >> "%LOG_FILE%" 2>&1

if %errorlevel% neq 0 (
    echo ERROR: Push failed >> "%LOG_FILE%"
    goto :FAIL
)

echo SUCCESS >> "%LOG_FILE%"
echo УСПЕШНО! Проверьте GitHub.
goto :END

:FAIL
echo Сбой. Проверьте лог git_log.txt.
echo FAILED >> "%LOG_FILE%"

:END
echo --- END LOG --- >> "%LOG_FILE%"
pause
