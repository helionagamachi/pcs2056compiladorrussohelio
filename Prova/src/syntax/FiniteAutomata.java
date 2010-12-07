package syntax;

import lex.Token;

import utils.CompilerException;
//import semantic.Semantic;

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
//    private Semantic semantics = new Semantic();
    private boolean[] states;
    private Transition[] transitions;
    private int automataNumber;
    private int currentState;
    //This will hold the transition that should be available when applicable
    private Transition transition;
    private boolean ok_Run_FinalTransition;
    

    /**
     * Constructs a new Finite Automata
     * @param stateAmount how many states will be needed to the automata?
     * @param automataNumber the identification number of this finite automata.
     */
    public FiniteAutomata(int stateAmount, int transitionsAmount, int automataNumber) {
        this.automataNumber = automataNumber;
        this.transitions = new Transition[transitionsAmount];
        this.states = new boolean[stateAmount];
        
    }

    /**
     * Initializes the list of states on the Automata, the
     * array indicates which states are final.
     * @param finalStates An array containing the indexes of the
     * final states for this automata, note that it should be
     * an ordered array in order to work
     */
    public void initStates(int[] finalStates) {
        
        this.currentState = 0;
        int done = 0;
        int finalStatesIndex = 0;
        boolean hasFinalStates;
        hasFinalStates = finalStates.length > 0;
        while (done < this.states.length) {
            boolean newState = false;
            if (hasFinalStates) {
                if (finalStates[finalStatesIndex] == done) {
                    
                    newState = true;
                    finalStatesIndex = finalStatesIndex + 1;
                    if (finalStatesIndex == finalStates.length) {
                        hasFinalStates = false;
                    }
                }
            }
            this.states[done] = newState;
            done = done + 1;
        }
    }

    public TransitionType transit(Token token) throws CompilerException {
        System.out.println("Looking for a transition, on Automata " + automataNumber);
        System.out.println("Current state :" + currentState);
        TransitionType possible;
        possible = searchTransition(TransitionType.NORMAL, token);
        if (possible == null) {
            possible = searchTransition(TransitionType.CALL_TO_ANOTHER_AUTOMATA, token);
        } else {
            //Got a normal transition.
            //adds the token to the list
//            semantics.addToken(token);
            String name = transition.getAction();
            if (name != null) {
//                semantics.runAction(name);
            }
        }
        if (possible != null) {
            return possible;
        }
        //Already checked for every possibility, so it's time to check
        //if it is in a final state, so it should return.
        if (states[currentState]) {
            System.out.println("The automata should return");
            // Try the final action of the machine.
//            semantics.runFinalAction(automataNumber);
            return TransitionType.GO_BACK;
        }

        CompilerException e = new CompilerException("No transition available");
        throw e;

    }

    /**
     * Searches a transition, based on the type give, if looking for a normal one, the token should be passed
     * to check compatibility
     * @param typeToSearch
     * @param token
     * @return null if no transition was found
     */
    private TransitionType searchTransition(TransitionType typeToSearch, Token token) {
        int transitionIndex = 0;
        while (transitionIndex < transitions.length) {
            Transition candidate = transitions[transitionIndex];
            if (candidate.getStateNumber() == this.currentState) {
                switch (typeToSearch) {
                    case NORMAL:
                        if (candidate.getType() == TransitionType.NORMAL) {
                            if (candidate.getToken().compatible(token)) {  
                                currentState = candidate.getNextState();
                                this.transition = candidate;
                                return TransitionType.NORMAL;
                            }
                        }
                        break;
                    case CALL_TO_ANOTHER_AUTOMATA:
                        if (candidate.getType() == TransitionType.CALL_TO_ANOTHER_AUTOMATA) {
                            
                            this.transition = candidate;
                            return TransitionType.CALL_TO_ANOTHER_AUTOMATA;
                        }

                }
            }
            transitionIndex++;
        }
        return null;
    }

    /**
     * Returns the transition set on the transition attribute
     * should be used when a call to another automata is encountered.
     * @return
     */
    public Transition getTransition() {
        return this.transition;
    }

    /**
     * Sets the array of transitions on the finite Automata.
     * @param transitions the transitions array to be set
     */
    public void setTransitions(Transition[] transitions) {
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
    public boolean onFinalState() {
        return this.states[this.currentState];
    }
}
