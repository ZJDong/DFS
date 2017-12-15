@ECHO OFF
@ECHO delete log files.
del "D:\web\logs\finderweb.com\*.log"

@IF exist "D:\MyApp\setenv.bat" call D:\MyApp\setenv.bat
@REM set TOMCAT_HOME=D:\Program Files\Tomcat-8.5.11

del "webapp\WEB-INF\classes\version.xml"
del "webapp\WEB-INF\upgrade\*.zip"
del "%TOMCAT_HOME%\logs\*.log"
del "%TOMCAT_HOME%\logs\*.txt"
rd /s /q "webapp\WEB-INF\ayada"

@ECHO   JAVA_HOME: %JAVA_HOME%
@ECHO TOMCAT_HOME: %TOMCAT_HOME%

copy "conf\server8.xml" "%TOMCAT_HOME%\conf\server.xml"
echo "conf\server8.xml" "%TOMCAT_HOME%\conf\server.xml"

cd /d "%TOMCAT_HOME%\bin"
startup.bat
