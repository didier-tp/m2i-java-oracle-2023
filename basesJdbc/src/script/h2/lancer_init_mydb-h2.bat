cd /d %~dp0
call set_env.bat

java -cp  %H2_CLASSPATH% org.h2.tools.RunScript  -user "sa" -url %MY_H2_DB_URL% -script personne-h2.sql


pause