/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package syntax;

import java.io.File;
import lex.Token;
import syntax.config.Parser;
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
                    stack.push(currentAutomata, nextState);
                    setAutomataAndState(transition.getNextAutomataNumber(), 0);
                    //The token hasn't been used yet!
                    return nextStep(token);
                case GO_BACK:
                    if (stack.isEmpty()) {
                        return false;
                    }
                    int[] result =  stack.pop();
                    setAutomataAndState(result[0], result[1]);
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

    /**
     * mostly used to reset the automata, sets the current finite automata and state.
     * @param automata
     * @param state
     */
    protected void setAutomataAndState(int automata, int state) {
        currentAutomata = automata;
        automatas[currentAutomata].setCurrentState(state);
    }


    
    /**
     * This method should read all the config files for the automatas and
     * create them.
     * @param files the array containing the path to the automatas' config
     * files.
     */
    public void init(String[] files) {
        File configFile;
        int index = 0;
        Parser parser;
        FiniteAutomata automata;
        while(index < files.length){
            configFile = new File(files[index]);
            parser = new Parser(configFile);
            automata = parser.parse();
            this.automatas[automata.getAutomataNumber()] = automata;
            index ++;
        }
        setAutomataAndState(0, 0);
    }
}
