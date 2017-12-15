@ECHO OFF

@REM finder restart
@REM http://www.finderweb.net
@set WORK_DIRECTORY=#{work.directory}
@set JSSE_OPTS=
@SET JAVA_PROCESS_ID=%1

@ECHO stop tomcat
@ECHO %WORK_DIRECTORY%\shutdown.bat
call %WORK_DIRECTORY%\shutdown.bat

@ECHO kill process: %JAVA_PROCESS_ID%
if not "%JAVA_PROCESS_ID%"=="" taskkill /F /pid %JAVA_PROCESS_ID%

@echo start tomcat
call %WORK_DIRECTORY%\startup.bat
@ECHO start success
