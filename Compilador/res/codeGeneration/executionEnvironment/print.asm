; Rotina de print
ALVO >   ; Inicio da String a ser impressa
PRINT >
& /0000 ; endereco inicial relocavel
ALVO K /0000 ; espero aqui o endereço da string a ser impressa
KDL K /8000 ; Comando de load é dado por 8 e o edereço
PASSO K /0002 ; passo de 2 bytes.
PRINT JP /000
	LD ALVO
LACO  + KDL
      MM X
	  X MM /000 ;  aqui a instrução de load dos bytes será reescrita
	  JZ FIM ; se tiver 0000 , vou para o final
	  PD /100 ; Imprime no monitor
	  LD ALVO
	  + PASSO ; adiciono 2 posições de memória
	  MM ALVO ; guardo no lugar.
	  JP LACO ; volto para o laco
FIM RS PRINT ;retorno de funcão 
# PRINT
