cd /d "%~dp0"
set ORACLE_HOME=C:\app\Administrateur\product\21c\dbhomeXE
set USERNAME=user2
set PASSWORD=pwd2
set SQL_SCRIPT=init-db.sql
"%ORACLE_HOME%\bin\sqlplus" %USERNAME%/%PASSWORD% @%SQL_SCRIPT%
