; testa TEMP1 == TEMP2

IGUAL >
TEMP1 <
TEMP2 <
TEMP3 <

& /0000
IGUAL	JP /0000
		LD TEMP1
		-  TEMP2
		JZ VERD
		LV /0000
		JP FIM
VERD	LV /0001
FIM		MM TEMP3
		RS IGUAL
		
# IGUAL