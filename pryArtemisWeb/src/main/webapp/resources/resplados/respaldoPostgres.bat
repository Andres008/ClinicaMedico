@echo off

REM Configuración de variables
set PG_BIN=C:\Program Files\PostgreSQL\16\bin  
set PG_USER=postgres
set PGPASSWORD=1003191044
set PG_DB=dbCeei
set BACKUP_DIR=D:\Mi unidad\Respaldos
set FECHA=%DATE:~-4%-%DATE:~3,2%-%DATE:~0,2%

REM Crear directorio de respaldo si no existe
if not exist "%BACKUP_DIR%" mkdir "%BACKUP_DIR%"

REM Ejecutar pg_dump con formato custom
"%PG_BIN%\pg_dump.exe" -U %PG_USER% -h localhost -p 5432 -F c -b -v -f "%BACKUP_DIR%\%PG_DB%_backup_%FECHA%.backup" %PG_DB%

REM Fin del script
