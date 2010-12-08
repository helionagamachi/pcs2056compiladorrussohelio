; NEWRA
; cria um novo registro de ativação.

; Base atual do registro , e o tamanho do novo registro.

BASE >
TAMANHO >     ; o tamanho não leva em consideracao o vetor de registros anteriores
COPIA >
; copia indica se os endereços dos anteriores devem ser copiados também.

TAMANHOATUAL    K       /0000
ANTERIORES      K       /0000
TEMP            K       /0000
CONT            K       /0000
K2		K 	/0002
KLD		K 	/8000
KMM             K       /9000
& /0000

NEWRA 	JP 	/0000
		LD 	COPIA 		; verifica se preciso copiar os ponteiros de outros RAs
		JZ	FIM		; se nao preciso, posso ir ao final.
                ; preciso fazer a copia dos registros de ativaçao , preciso recuperar quantos o anterior tem.
                LD      BASE            ; Carrego a base
                +       KLD             ; Base é um ponteiro.
                MM      QTDANT          ; escrevo nova instruçao
QTDANT          JP      /0000           ; load de quantos registros de ativaçao o anterior tem.
                MM      ANTERIORES      ; Guardo a informação.
                *       K2              ;
                +       TAMANHO         ;
                +       K2              ;
                MM      TAMANHO         ; O novo tamanho do registro de ativaçao é atualizado
                LD      BASE            ; coloco o valor da base em um TEMPORARIO
                MM      TEMP            ;
                                        

FIM 	LD 	BASE			; Carrego a base atual
		+	TAMANHO		; Somo com o tamanho do novo RA.
		MM 	BASE 		; O ponteiro de base passa a apontar para o novo RA
                +       KMM             ; instruçao para armazenar o tamanho
                MM      WRTAMANHO       ; escrevo a instrucao
WRTAMANHO       JP      /0000           ; guardo o tamanho
                LD      BASE            ; preciso guardar quantos anteriores tem...
                -       K2              ; alinhando
                +       KMM             ; ajustando posicao
                MM      WRANTERIORES    ; escreve a instrucao
WRANTERIOES     JP      /0000           ; escreve a quantidade de anteriores.
		RS 	NEWRA		; retorno de função
		
		



# NEWRA
