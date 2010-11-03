/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package syntatical;

/**
 * Class to be used on the Finite Automata of the structed automata.
 * @author helionagamachi
 */
public class FiniteAutomata {

    private FiniteAutomataState[] states;
    private Transition[] transitions;
    private int automataNumber;
    private int currentState;

    /**
     * Constructs a new Finite Automata
     * @param stateAmount how many states will be needed to the automata?
     * @param automataNumber the identification number of this finite automata.
     */
    public FiniteAutomata(int stateAmount, int transitionsAmount,int automataNumber) {
        this.automataNumber = automataNumber;
        this.transitions = new Transition[transitionsAmount];
        this.states = new FiniteAutomataState[stateAmount];
    }

    /**
     * Initializes the list of states on the Automata, the
     * array indicates which states are final.
     * @param finalStates An array containing the indexes of the
     * final states for this automata, note that it should be
     * an ordered array in order to work
     */
    public void initStates(int[] finalStates){
        this.currentState = 0;
        int statesAmount = this.states.length;
        int done = 0;
        int finalStatesIndex =0 ;
        boolean hasFinalStates;
        hasFinalStates = finalStates.length > 0;
        while(done < statesAmount){
            FiniteAutomataState newState;
            boolean isFinalState = false;
            if(hasFinalStates){
                if(finalStates[finalStatesIndex] == done){
                    isFinalState = true;
                    finalStatesIndex = finalStatesIndex + 1;
                    if(finalStatesIndex == finalStates.length){
                        hasFinalStates = false;
                    }
                }
            }
            newState = new FiniteAutomataState(isFinalState, done);
            this.states[done] = newState;
            done = done + 1;
        }
    }

    public int getAutomataNumber() {
        return automataNumber;
    }

    public int getCurrentState() {
        return currentState;
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }

    /**
     * @return the automata is on a final state or note.
     */
    public boolean onFinalState(){
        return this.states[this.currentState].isFinalState();
    }
   
}
