; TESTA TEMP1 >= TEMP2

MAIORIG >
TEMP1 <
TEMP2 <
TEMP3 <

& /0000

MAIORIG JP /0000
		LD TEMP1
		-  TEMP2
		JN FALSO
		LV /0001
		JP FIM
FALSO	LV /0000
FIM		MM TEMP3
		RS MAIORIG
# MAIORIG
