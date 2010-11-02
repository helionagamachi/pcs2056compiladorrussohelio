/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package syntatical;

import org.apache.log4j.Logger;

/**
 * This class represents a state of a finite automata state
 * @author helionagamachi
 */
public class FiniteAutomataState {
    private boolean finalState;
    private int number;
    private static Logger LOGGER = Logger.getLogger(FiniteAutomataState.class);

    /**
     * Constructs a state
     * @param finalState boolean that indicates if the state is final or not
     * @param number the number of the state, given for debug .
     */
    public FiniteAutomataState(boolean finalState, int number) {
        this.finalState = finalState;
        this.number = number;
        LOGGER.debug("Create a new Finite State Automata, with number " + this.number);
        LOGGER.debug("The FinalState attribute is " + this.finalState);
    }

    public int getNumber() {
        return number;
    }


    public boolean isFinalState(){
        return this.finalState;
    }


    
}
