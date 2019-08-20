@echo off
SET DISTDIR=.\dist\mean-angular6
SET STDIR=..\spring-rest-backend\src\main\resources\static

del /F /Q %STDIR%\*
xcopy /Y %DISTDIR%\* %STDIR%