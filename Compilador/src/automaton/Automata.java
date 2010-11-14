/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automaton;

import lex.Token;

/**
 *
 * @author Helio
 */
public abstract class Automata {

    protected State currentState = State.INITIAL;
    

    /**
     * The automaton receives the character, if it can still
     * process another char returns true, if it is on the final state or
     * reached an error state, returns false.
     * @param a the character to be consumed
     * @return true if it can continue to receive more chars, or false if it
     * is in the final state or an error state.
     */
    public abstract boolean processChar(char a);


    /**
     * Gets the current state of the automaton.
     * @return the state
     */
    public State getState(){
      return this.currentState;
    };

    /**
     * Resets the automata to the initial state.
     */
    public void resetAutomata(){
        this.currentState = State.INITIAL;
    }

    /**
     * Gets the token to the machine state, if none, null should be returned
     * This method should be used only if the automata reaches a final state;
     * @return the token
     */
    public abstract Token getToken();


    
    @Override
    public abstract String toString();

}
