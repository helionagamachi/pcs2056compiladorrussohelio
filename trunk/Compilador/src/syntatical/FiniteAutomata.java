/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package syntatical;

import lex.Token;
import org.apache.log4j.Logger;
import utils.CompilerException;


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
    //This will hold the transition that should be available when applicable
    private Transition transition;
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
        LOGGER.debug("New Automata created, has " + stateAmount + " states and " + transitionsAmount + " transitions");
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


    public TransitionType transit(Token token) throws CompilerException{
        //TODO:Varrer o vetor de transições, buscando um que tenha o estado corrente
        LOGGER.debug("Looking for a transition, on Automata " + automataNumber);
        LOGGER.debug("Current state :" + currentState);
        
        int transitionIndex  = 0 ;
        while(transitionIndex < transitions.length){
            Transition candidate = transitions[transitionIndex];
            if(candidate.getStateNumber() == this.currentState){
                //Normal or Call to another?
                if(candidate.getType() == TransitionType.NORMAL){
                    if(candidate.getToken().equals(token)){
                        LOGGER.debug("Found a normal transition");
                        currentState = candidate.getNextState();
                        LOGGER.debug("now on state" + currentState);
                        return TransitionType.NORMAL;
                    }
                    //Nothing to do here...
                    //Look for the next one.
                }else{
                    //The candidate to the transition should be set here.
                    this.transition = candidate;
                    return TransitionType.CALL_TO_ANOTHER_AUTOMATA;
                }
            }
            transitionIndex ++;
        }

        
        //TODO:Verificar se o tipo de transição é normal
        //TODO:se for, mudar o current state
        //TODO:se não, retornar que o tipo é chamada para outro...
        //Disponibilizar a transição...
        CompilerException e = new CompilerException("No transition available");
        throw e;
        
    }


    /**
     * Sets the array of transitions on the finite Automata.
     * @param transitions the transitions array to be set
     */
    public void setTransitions(Transition[] transitions){
        this.transitions = transitions;
    }

    /**
     * 
     * @return returns the number of the automata
     */
    public int getAutomataNumber() {
        return automataNumber;
    }

    /**
     *
     * @return return the number of the current state
     */
    public int getCurrentState() {
        return currentState;
    }

    /**
     * Sets the current State of the automata, to be used by the structed
     * automata, when a automata returns, or when a automata is called
     * @param currentState the number to the new current state.
     */
    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }

    /**
     * @return the automata is on a final state or note.
     */
    public boolean onFinalState(){
        return this.states[this.currentState];
    }



}
