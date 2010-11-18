/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package automaton;

import lex.Token;
import lex.TokenType;

/**
 * Class that should be able to process comments.
 * @author Helio
 */
public class CommentAutomata extends Automata {

    @Override
    public boolean processChar(char a) {
        //Depending on the state, uses diferent
        boolean result = false;
        switch (this.currentState) {
            case INITIAL:
                if (a == '/') {
                    this.currentState = State.COMMENT_START;
                    result = true;
                }
                break;
            case COMMENT_START:
                if (a == '/') {
                    this.currentState = State.COMMENT_LINE;
                    result = true;
                } else if (a == '*') {
                    this.currentState = State.COMMENT_BLOCK;
                    result = true;
                } else {
                    this.currentState = State.ERROR;
                }
                break;
            case COMMENT_LINE:
                if (a == '\n') {
                    //end of the comment line
                    this.currentState = State.COMMENT_END;
                }else{
                    // go on processing.
                    result = true;
                }
                break;
            case COMMENT_BLOCK:
                if (a == '*') {
                    //Possibly the comment block will end
                    this.currentState = State.COMMENT_BLOCK_END;
                }
                result = true;
                break;
            case COMMENT_BLOCK_END:
                if (a == '/') {
                    // The comment block will really end
                    this.currentState = State.COMMENT_END;
                    result = true;
                } else {
                    // Block not ended
                    result = true;
                    if (a == '*') {
                        // preserves the state, may end in the next char
                    } else {
                        // goes back to the other state
                        this.currentState = State.COMMENT_BLOCK;
                    }
                }
        }
        return result;
    }

    /**
     * The comment automata should not return a token
     */
    @Override
    public Token getToken() {
        return new Token(TokenType.COMMENT, 0);
    }

    @Override
    public String toString() {
        return "Comment Automata, on State " + currentState;
    }




}

