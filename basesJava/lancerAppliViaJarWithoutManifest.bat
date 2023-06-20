REM set JAVA_HOME=C:\Program Files\java\jdk17
set JAVA_HOME=C:\Users\Administrateur\.p2\pool\plugins\org.eclipse.justj.openjdk.hotspot.jre.full.win32.x86_64_17.0.7.v20230425-1502\jre
set PATH=%JAVA_HOME%\bin
cd "%~dp0"
cd target
java -classpath basesJava.jar tp.MyApp
pause