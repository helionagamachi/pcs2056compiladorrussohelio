; LOAD VAR
; código destinado a lidar com a cópia de variáveis, baseado  na ideia
; de registros de ativacao
; VAR é a label que indica a distancia da variavel da base.
; No final a copia da variavel fica no acumulador
; funciona apenas para variaveis de 2 bytes
LDVAR >
VAR <
BASE <

& /0000 ;

KLD  K /8000 ; constante para load

LDVAR	JP 	/0000  ; inicio da funcao
		LD	BASE  
		- 	VAR    ; correcao da base
		+	KLD    ; soma 
		MM	RESULT ;
RESULT 	JP	/0000  ; aqui o load do valor da variavel , dentro do registro de ativacao ocorre.
		RS	LDVAR  ; Fim da funcao
# LDVAR