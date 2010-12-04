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
TEMP3   K /0000
ZERO0   K /0000
 CONST0  K =1 
 CONST1  K =0 
 CONST2  K =0 
 VAR0  K =0 
 VAR1  K =2 
 VAR2  K =4 
 VAR3  K =6 
 VAR4  K =28 


LD  CONST0 
MM TEMP1 
LD CONST1 
MM TEMP2 
SC AND
MM  CONST2 
