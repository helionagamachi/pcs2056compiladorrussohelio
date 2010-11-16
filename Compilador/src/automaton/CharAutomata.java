/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automaton;

import lex.Token;
import lex.TokenType;
import static utils.EscapeChars.escapeChar;

/**
 * Automata to read chars they are denoted by a having a letter between simple
 * quotes
 * @author helionagamachi
 */
public class CharAutomata extends Automata {


    private char c;
    public CharAutomata() {
        this.currentState = State.INITIAL;
    }



    @Override
    public boolean processChar(char a) {
        switch(currentState){
            case INITIAL:
                if(a == '\''){
                    currentState = State.ONE_QUOTE;
                    return true;
                }
                break;
            case ONE_QUOTE:
                if(a == '\\'){
                    currentState = State.POSSIBLE_ESCAPE_CHAR;
                return true;
                }else if(a == '\''){
                    // there is no char at all
                    currentState = State.ERROR;
                }else{
                    this.c = a;
                    currentState = State.POSSIBLE_CHAR;
                    return true;
                }
                break;
            case POSSIBLE_CHAR:
                if(a=='\''){
                    currentState = State.CHAR;
                }else{
                    currentState = State.ERROR;
                }
                break;
            case POSSIBLE_ESCAPE_CHAR:
                this.c = escapeChar(a);
                if(c == ' '){
                    currentState = State.ERROR;
                }else{
                    currentState = State.POSSIBLE_CHAR;
                    return true;
                }
                break;
        }
        return false;
    }

    @Override
    public Token getToken() {
        return new Token(TokenType.CHAR, this.c);
    }

    @Override
    public String toString() {
        return "Char automata on state " + currentState;
    }

}
