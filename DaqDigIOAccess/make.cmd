@echo off
echo Compile the C Code to a DLL
cl -IC:\j2sdk1.4\include -IC:\j2sdk1.4\include\win32  -LD DaqRoutines.cpp DaqDigIOAccessImpl.C -FeDaqDigIOAccessImpl.dll C:\Programs\BoyScouts\LIB\DAQX.lib
del *.obj

REM C:\Programs\BoyScouts\LIB\BCB5DaqX.lib 
REM C:\Programs\BoyScouts\LIB\DAQX.lib


echo Done.
