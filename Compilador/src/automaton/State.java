/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automaton;

/**
 *
 * @author Helio
 */
public enum  State {
    //Default states for every automata.
    INITIAL,  ERROR,
    //States for the comment automata.
    COMMENT_START, COMMENT_LINE , COMMENT_BLOCK , COMMENT_BLOCK_END, COMMENT_END(true),
    //States for number automata,
    INTEGER(true), FLOAT(true), FLOAT_STARTED_BY_DOT,
    //States for String,
    STRING_CONTENT, STRING_FINAL(true),
    //For reserved words and identifiers...
    POSSIBLE_RESERVED_WORD, POSSIBLE_OPERATOR, RESERVED_WORD(true), IDENTIFIER(true),
    ;
    
    private boolean finalState;
    private State(){
        this.finalState = false;
    }
    private State(boolean finalState){
        this.finalState = finalState;
    }

    public boolean isFinalState(){
        return this.finalState;
    }
}
