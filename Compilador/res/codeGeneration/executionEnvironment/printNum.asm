; print numero 

PRINTI >
NUM    >

& /0000

;JP	TESTE

ZERO	K	/0030
ALG	K	/0000
NUM	K	/0000
TEMP	K	/0000
DEZ	K	=10
ESP	$	=10
PONT	K	ESP
CONT	K	=0
K1	K	=1
K2	K	/2
KLD	K	/8000
KMM	K	/9000

;TESTE	SC	PRINTI
;	HM	TESTE




PRINTI	JP	/0000
	SC	INIC
	SC	TRANS
STATP	LD	PONT
	+	KLD
	MM	LOADP
LOADP	JP	/0000
	MM	ALG
	SC	PALG
	LD	CONT
	-	K1
	MM	CONT
	JZ	FIMP
	LD	PONT
	+	K2
	MM	PONT
	JP	STATP
FIMP	RS	PRINTI
	
	
INIC	JP	/0000
	LV	ESP
	MM	PONT
	LV	/0000
	MM	CONT
	MM	TEMP
	RS	INIC




TRANS	JP	/0000   ; transforma o numero em códigos ascii.
	LD	NUM	; pego numero
	*	K2
	-	K2
	+	PONT
	MM	PONT	; coloca o ponteiro no final do espaco
	SC	VER	; peco a verificacao
LACO	LD	NUM
	JZ	FIMT    
	SC	UNI
	MM	TEMP
	LD	PONT
	+	KMM
	MM	POS 	; prepara a instrucao para grava na memoria
	LD	TEMP
POS	JP	/0000	; grava na memoria
	LD	PONT
	-	K2
	MM	PONT	; atualiza o ponteiro
	LD	CONT	
	+	K1	
	MM	CONT	; atualiza o contador.
	LD	NUM
	/	DEZ
	MM	NUM	; atualiza o numero
	JP	LACO	; laco
FIMT	LD	PONT
	+	K2
	MM	PONT
	RS	TRANS



VER	JP	/0000	; verifica se o numero é negativo
	LD	NUM
	JN	MENOS
	/	DEZ	; preciso verificar se não é apenas uma unidade	
	JZ	APENA	; é apenas uma unidade
	RS	VER
APENA	LD	NUM
	MM	ALG
	SC	PALG
	JP	FIMP
MENOS   SC	NEGA
	RS	VER
	
	


NEGA	JP	/0000	; imprime o sinal de menos, e ..
	LV	=45
	PD	/100    ; nega o numero negativo, e imprime o sinal de menos.
	LV	/0000
	-	NUM
	MM	NUM
	RS	NEGA

UNI	JP	/0000	; PEGA A UNIDADE DE UM NUMERO
	LD	NUM	;
	/	DEZ	;
	*	DEZ	
	MM	TEMP
	LD 	NUM 	
	-	TEMP
	RS	UNI
	
PALG	JP	/0000 ; IMPRIME UM ALGARISMO
	LD	ALG
	+	ZERO
	PD	/100 ; IMPRIME
	RS	PALG

# LOL