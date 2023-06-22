cd /d %~dp0
call set_env.bat

java -jar  %H2_CLASSPATH% -user "sa" -url %MY_H2_DB_URL%


REM NB: penser à se daconnecter
REM    et options/session actives/arret pour eviter des futurs verrous/blocages

pause