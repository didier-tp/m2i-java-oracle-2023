REM : lancer ce .bat en tant qu'administrateur
REM  pour que windows+vagrant puisse créer des symlinks si nécessaire
cd /d %~dp0
vagrant up
cmd