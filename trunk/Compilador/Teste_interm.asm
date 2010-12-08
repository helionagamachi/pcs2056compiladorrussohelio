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
 CONST0  K =1 
 CONST1  K =0 
 VAR0  K =0 
 VAR1  K =0 
 VAR2  K =0 
 VAR3  K =0 
 VAR4  K =0 
LD  CONST0 
JZ LB0 
LD  VAR0 
MM TEMP1 
LD VAR1 
MM TEMP2 
SC MENORIG
LD TEMP3 
MM  CONST1 
MM  VAR2 
 LB0 OS /0000
