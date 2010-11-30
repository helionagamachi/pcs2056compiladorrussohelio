; ambiente basico de execucao
; para uso basico da funcao print
PRINT <
ALVO  <
; para uso basico da funcao de copia de variavel para a memoria
CPVAR <
VAR   >
; Indica o inicio do registro de ativacao atual.
BASE  >
; Teclado ?
& /0000 ;
JP INICIO
; Some stuff is testing yet...
	K /0002
	K /0003
BASE    K /0004
VAR     K /0000
TEMP1   K /0000
TEMP2   K /0000
 CONST0  K =9 
 CONST1  K =3 
 CONST2  K =0 
 CONST3  K =4 
 CONST4  K =5 
 CONST5  K =0 
 CONST6  K =0 
 CONST7  K =8 
 CONST8  K =0 
 CONST9  K =9 
 CONST10  K =0 
 VAR0  K =0 
 VAR1  K =2 
 VAR2  K =4 
 VAR3  K =6 
 VAR4  K =8 


LD  CONST0 
- CONST1 
MM CONST2 

LD  CONST3 
* CONST4 
MM CONST5 

LD  CONST2 
+ CONST5 
MM CONST6 

LD  CONST6 
- CONST7 
MM CONST8 

LD  CONST8 
+ CONST9 
MM  CONST10 
