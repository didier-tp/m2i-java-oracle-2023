Lancement console web de H2�(url=http://localhost:8082):
cd h2/bin
java -jar  h2*.jar (ou bien h2.bat ou bien double click sur h2*.jar)

NB: penser à se deconnecter
    et options/session actives/arret pour eviter des futurs verrous/blocages


*****************

Lancement d'un script sql (h2):
java -cp ...h2.jar org.h2.tools.RunScript -url jdbc:h2:~/test -user sa -script xy.sql