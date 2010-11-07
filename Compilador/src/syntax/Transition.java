/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package syntax;

import lex.Token;

/**
 * This class represents a transition of states in a finite Automata
 * @author helionagamachi
 */
public class Transition {

    private int automataNumber;
    private int stateNumber;
    private int nextState;
    private int nextAutomataNumber;
    private Token token;
    private TransitionType type;

    /**
     * This constructor is destined to a transition that will consume a token
     * @param automataNumber the number of the Automata that has this transition
     * @param stateNumber the state that originates the transition
     * @param nextState the next state number, should be the current state after the transition
     * @param token the token that will be consumed
     */
    public Transition(int automataNumber, int stateNumber, int nextState, Token token) {
        this.automataNumber = automataNumber;
        this.stateNumber = stateNumber;
        this.nextState = nextState;
        this.token = token;
        this.type = TransitionType.NORMAL;
    }

    /**
     * This constructor is to a Transition that will lead to a call to another
     * automata
     * @param automataNumber the number of the Automata that has this transition
     * @param stateNumber the state that originates the transition
     * @param nextState the state on which  the automata should resume
     * @param otherAutomataNumber the number of the automata that should be called
     */
    public Transition(int automataNumber, int stateNumber, int nextState, int otherAutomataNumber) {
        this.automataNumber = automataNumber;
        this.stateNumber = stateNumber;
        this.nextState = nextState;
        this.nextAutomataNumber = otherAutomataNumber;
        this.type = TransitionType.CALL_TO_ANOTHER_AUTOMATA;
        this.token = null;
    }

    
    public int getAutomataNumber() {
        return automataNumber;
    }

    public int getNextState() {
        return nextState;
    }

    public int getNextAutomataNumber() {
        return nextAutomataNumber;
    }

    public int getStateNumber() {
        return stateNumber;
    }

    public Token getToken() {
        return token;
    }

    public TransitionType getType() {
        return type;
    }

}
