/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lex.automaton;

import lex.Token;

/**
 * Puramente para fazer com que os automatos sigam a mesma linha de métodos.
 * @author helionagamachi
 */
public abstract class Automaton {
    /**
     * O automato só deve retornar falso quando o caractere c não pode ser mais
     * processado pelo automato.
     * @param c
     * @return
     */
    public abstract boolean process(char c);
    public abstract boolean isOnFinalState();
    /**
     * Reseta o automato, para seu estado inicial
     */
    public abstract void reset();
    /**
     * retorna o token da máquina, deve ser 
     * chamado quando está em um estado final.
     * @return
     */
    public abstract Token getToken();

}
