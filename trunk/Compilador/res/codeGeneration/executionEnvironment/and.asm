; Funcao para o AND LÓGICO, PRESUME-SE QUE OS 
; OPERADORES SÃO BOOLEANOS, 1 E 0 , CASO CONTRARIO
; RESULTADOS INESPERADOS PODEM ACONTECER.

; os operandos devem estar carregados nos labels temp1 e temp2
AND >
TEMP1 < 
TEMP2 <
TEMP3 <  ; resultado fica em temp3
& /0000 ; origem relocavel
AND JP /0000  
	LD TEMP1
	*  TEMP2
	MM TEMP3
FIM	RS AND

# AND
	

