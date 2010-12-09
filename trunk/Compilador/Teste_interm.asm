; ambiente basico de execucao
; Temporarias de uso basico para diversas funcoes
TEMP1 >
TEMP2 >
TEMP3 >
; para uso basico da funcao print
PRINT <
ALVO  <
; funcoes logicas
OU <
AND <
NOT <
; funcoes comparativas
MAIOR <
MENOR <
IGUAL <
DIF <
MAIORIG <
MENORIG <
& /0000 ;
TEMP1   K /0000
TEMP2   K /0000
TEMP3   K /0000
ZERO0   K /0000
INICIO  OS  /0000
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
HM INICIO 
 # FIM