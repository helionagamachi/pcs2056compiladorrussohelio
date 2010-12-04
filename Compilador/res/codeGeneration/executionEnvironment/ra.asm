; NEWRA
; cria um novo registro de ativação.

; Base atual do registro , e o tamanho do novo registro.

BASE >
TAMANHO >     ; o tamanho não leva em consideracao o vetor de registros anteriores
COPIA >
; copia indica se os endereços dos anteriores devem ser copiados também.

TAMANHOATUAL K /0000
ANTERIORES   K /0000
K2		K 	/0002
KLD		K 	/8000
& /0000

NEWRA 	JP 	/0000
		LD 	COPIA 		; verifica se preciso copiar os ponteiros de outros RAs
		JZ	FIM		; se nao preciso, posso ir ao final.
                ; preciso fazer a copia dos registros de ativaçao , preciso recuperar quantos o anterior tem.
                
FIM 	LD 	BASE			; Carrego a base atual
		+	TAMANHO		; Somo com o tamanho do novo RA.
		MM 	BASE 		; O ponteiro de base passa a apontar para o novo RA
		RS 	NEWRA		; retorno de função
		
		



# NEWRA
