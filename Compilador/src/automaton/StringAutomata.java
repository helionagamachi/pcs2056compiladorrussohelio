/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package automaton;

import lex.Token;

/**
 * Class destined to take care of strings
 * and characters
 * @author Helio
 */
public class StringAutomata extends Automata {

    private String content;

    public StringAutomata() {
    }

    @Override
    public boolean processChar(char a) {
        boolean result = false;
        switch (this.currentState) {
            case INITIAL:
                if (a == '"') {
                    result = true;
                    this.currentState = State.STRING_CONTENT;
                }
                break;
            case STRING_CONTENT:
                if (a == '"') {
                    this.currentState = State.STRING_FINAL;
                    // TODO : Treat scape chars.
                } else {
                    content += a;
                    result = true;
                }
                break;
        }
        return result;
    }

    @Override
    public Token getToken() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * For debugging
     * @return the string that was mounted until now
     */
    public String getContent(){
        return this.content;
    }

    @Override
    public void resetAutomata() {
        this.currentState = State.INITIAL;
        this.content = "";
    }
}
