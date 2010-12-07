/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lex.automaton;

import lex.Token;
import lex.TokenType;
import static utils.ArrayUtils.numbers;
import static utils.ArrayUtils.charIsOnArray;

/**
 * Automata responsável pelos numeros
 * @author helionagamachi
 */
public class NumberAutomaton extends Automaton {

    private boolean finalState;
    private Token token;
    private boolean isFloat;
    private int part1;
    private int part2;
    @Override
    public boolean process(char c) {
        if (c == '.') {
            if (isFloat) {
                prepareToken();
                return false;
            } else {
                isFloat = true;
                return true;
            }
        } else if (charIsOnArray(c, numbers)) {
            finalState = true;
            if (isFloat) {
                part2 = part2 * 10 + (c - 0x30);
            } else {
                part1 = part1 * 10 + (c - 0x30);
            }
            return true;
        } else {
            if (finalState) {
                prepareToken();
            }
            return false;
        }
        
    }

    @Override
    public boolean isOnFinalState() {
        return finalState;
    }

    @Override
    public void reset() {
        finalState = false;
        token = null;
        isFloat = false;
        part1 = 0;
        part2 = 0;
    }

    @Override
    public Token getToken() {
        return token;
    }

    private void prepareToken() {
        token = new Token(TokenType.NUMBER, part1);
        token.setValue2(part2);
    }

    

}
