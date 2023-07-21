cd /d "%~dp0"
set MYSQL_HOME=C:\Program Files\MariaDB 10.10
"%MYSQL_HOME%\bin\mysql" -u root -p < init-db.sql > res.txt 2> err.txt
