/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package syntax;

import lex.Token;
import utils.CompilerException;

/**
 *
 * @author helionagamachi
 */
public class StructedAutomata {

    private FiniteAutomata[] automatas;
    private AutomataStack stack;
    private int currentAutomata;

    public StructedAutomata(int automataAmount) {
        stack = AutomataStack.getInstance();
        automatas = new FiniteAutomata[automataAmount];
        setAutomataAndState(0, 0);
    }

    /**
     * Takes on the next step on the automata, by using the token
     * @param token from the lexical analyzer.
     * @return true if it is ok to continue, false if not, check if it
     * reached the final state of the 0 automata, if not, there is
     * a syntax error.
     */
    public boolean nextStep(Token token) {
        try {
            TransitionType returned = automatas[currentAutomata].transit(token);
            switch (returned) {
                case NORMAL:
                    return true;
                case CALL_TO_ANOTHER_AUTOMATA:
                    Transition transition = automatas[currentAutomata].getTransition();
                    int nextState = transition.getNextState();
                    AutomataStackElement element = new AutomataStackElement(currentAutomata, nextState);
                    stack.push(element);
                    setAutomataAndState(transition.getNextAutomataNumber(), 0);
                    //The token hasn't been used yet!
                    return nextStep(token);
                case GO_BACK:
                    if (stack.isEmpty()) {
                        return false;
                    }
                    element = stack.pop();
                    setAutomataAndState(element.getAutomataNumber(), element.getStateNumber());
                    return nextStep(token);
                default:
                    return false;
            }
        } catch (CompilerException ex) {
            return false;
        }

    }

    /**
     * Indicates if the program is accepted by the Structed Automata,
     * should be used when the nextStep hasn't raised any exception
     * and returned false
     * @return true if the automata is in an acceptance state.
     */
    public boolean accepted() {
        boolean isInitialAutomata, isFinalState;
        isInitialAutomata = currentAutomata == 0;
        isFinalState = automatas[0].onFinalState();
        return isFinalState && isInitialAutomata;
    }

    private void setAutomataAndState(int automata, int state) {
        currentAutomata = automata;
        automatas[currentAutomata].setCurrentState(state);
    }

    /**
     * This method should read all the config files for the automatas and
     * create them.
     */
    public void init() {
        //TODO: a parser to get the config of the automatas...
    }
}
