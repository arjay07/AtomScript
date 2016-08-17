@echo off
set "value=%*;%PATH%"
If NOT "%PATH%"=="%PATH:AtomScript=%" ( 

	echo Yes

) else ( 

	setx -m PATH "%value%" 
	
)