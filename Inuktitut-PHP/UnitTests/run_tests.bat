@echo off

set filter=
set tests=AllTests.php

if "%1"=="" (
	goto EXEC
) else if "%1"=="-h" (
	call:usage
	goto:eof
) else if "%1"=="-m" (
	set tests=%2/AllTests.php
) else if "%1"=="-c" (
	set filter=--filter %2Test
) else if "%1"=="-t" (
	set filter=--filter %2
) else (
	call:usage
	goto:eof
)

:EXEC
echo ***** run_tests.bat --- phpunit -d display_errors=on --include-path .;c:/Users/farleyb/Documents/BenoitFarley/Inuktitut-PHP %filter% %tests%
phpunit -d display_errors=on --include-path .;c:/Users/farleyb/Documents/BenoitFarley/Inuktitut-PHP %filter% %tests%



:usage
echo Usage: run_tests.bat
echo        run_tests.bat -d ^<directory^>
echo        run_tests.bat -c ^<class^>
echo        run_tests.bat -t ^<test^>
echo.
echo        No argument       execute all tests in all subdirectories
echo        -d ^<directory^>    execute all tests in subdirectory ^<directory^>
echo        -c ^<class^>        execute all tests on class ^<class^> in file ^<class^>Test.php
echo        -t ^<test^>         execute the specific test ^<test^>
echo.
goto:eof
