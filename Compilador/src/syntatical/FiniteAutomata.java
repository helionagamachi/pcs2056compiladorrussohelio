/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package syntatical;

import lex.Token;
import org.apache.log4j.Logger;


/**
 * Class to be used on the Finite Automata of the structed automata.
 * @author helionagamachi
 */
public class FiniteAutomata {
    /**
     * Basically all i need to know about a state it is if is a final state
     * and it's number, which will be the position on the array, the boolean says
     * if it is a final state or not.
     */

    private boolean[] states;
    private Transition[] transitions;
    private int automataNumber;
    private int currentState;
    private static Logger LOGGER = Logger.getLogger(FiniteAutomata.class);

    /**
     * Constructs a new Finite Automata
     * @param stateAmount how many states will be needed to the automata?
     * @param automataNumber the identification number of this finite automata.
     */
    public FiniteAutomata(int stateAmount, int transitionsAmount,int automataNumber) {
        this.automataNumber = automataNumber;
        this.transitions = new Transition[transitionsAmount];
        this.states = new boolean[stateAmount];
        LOGGER.debug("New Automata created, has " + states + " states and " + transitions + " transitions");
    }

    /**
     * Initializes the list of states on the Automata, the
     * array indicates which states are final.
     * @param finalStates An array containing the indexes of the
     * final states for this automata, note that it should be
     * an ordered array in order to work
     */
    public void initStates(int[] finalStates){
        LOGGER.debug("initStates called");
        this.currentState = 0;
        int done = 0;
        int finalStatesIndex =0 ;
        boolean hasFinalStates;
        hasFinalStates = finalStates.length > 0;
        while(done < this.states.length){
            boolean newState = false;
            if(hasFinalStates){
                if(finalStates[finalStatesIndex] == done){
                    LOGGER.debug("state number " + done  + " is a final state");
                    newState = true;
                    finalStatesIndex = finalStatesIndex + 1;
                    if(finalStatesIndex == finalStates.length){
                        hasFinalStates = false;
                    }
                }
            }
            this.states[done] = newState;
            done = done + 1;
        }
    }


    public Transition.Type transit(Token token) throws compilerExcetion{
        //TODO:Varrer o vetor de transições, buscando um que tenha o estado corrente
        //TODO:Verificar se o tipo de transição é normal
        //TODO:se for, mudar o current state
        //TODO:se não, retornar que o tipo é chamada para outro...
        //Disponibilizar a transição...
        

        return Transition.Type.NORMAL;
    }


    /**
     * Sets the array of transitions on the finite Automata.
     * @param transitions the transitions array to be set
     */
    public void setTransitions(Transition[] transitions){
        this.transitions = transitions;
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
        return this.states[this.currentState];
    }


    public class compilerExcetion extends Exception{
        
    }

}
