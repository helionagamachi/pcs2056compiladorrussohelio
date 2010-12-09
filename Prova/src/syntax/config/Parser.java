/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package syntax.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import lex.Token;
import lex.TokenType;
import syntax.FiniteAutomata;
import syntax.Transition;
import static lex.ReservedWords.getIndex;
import static utils.ArrayUtils.whiteSpace;
import static utils.ArrayUtils.charIsOnArray;

/**
 * This class is dedicated to parse the files with
 * the configuration to the finite automatas , used to syntax analysis
 * @author helionagamachi
 */
public class Parser {

    // This list will be used to make equivalence on the name to the automata
    // number.
    // the namesLIst should be already filled...
    private static ArrayList<String> namesList = new ArrayList<String>();
    private File source;
    private int statesAmount;
    private ArrayList<Transition> toCorrect = new ArrayList<Transition>();

    /**
     * The names list should contain the names of the automatas.
     * @param source
     * @param namesList
     */
    public Parser(File source) {
        this.source = source;
        this.statesAmount = 0;
        if (namesList.isEmpty()) {
//            namesList.add("bloco");
            namesList.add("comando");
            namesList.add("funcao");
            namesList.add("exp");
        }
    }

    public FiniteAutomata parse() throws IOException {
        System.out.println("Parsing automata : " + source.getName());
        this.statesAmount = 0;
        try {

            //The first line I alread know, just go to the next one.
            FileReader r = new FileReader(source);
            BufferedReader reader = new BufferedReader(r);
            reader.readLine();
            String secondLine = reader.readLine();
            int[] finalStates = parseSecondLine(secondLine);
            String transitionLine = reader.readLine();
            ArrayList<Transition> transList = new ArrayList<Transition>();
            while (transitionLine != null) {
                Transition trans = parseTransition(transitionLine);
                if (trans != null) {
                    transList.add(trans);
                }
                transitionLine = reader.readLine();
            }
            Transition[] transitions = new Transition[0];
            transitions = transList.toArray(transitions);
            transitions = correctTransitions(transitions);
            FiniteAutomata automata = new FiniteAutomata(statesAmount, transitions.length, namesList.indexOf(source.getName()));
            automata.initStates(finalStates);
            automata.setTransitions(transitions);
            return automata;

        } catch (FileNotFoundException ex) {
            //Should not happen
            System.out.println("An error with the file occured!");
            return null;
        }
    }

    /**
     * Returns the arrays with the final states numbers.
     * @param secondLine
     * @return the array containing the final states numbers .
     */
    protected int[] parseSecondLine(String secondLine) {
        int size = secondLine.length();
        ArrayList<String> listing = new ArrayList<String>();
        int index = 6;
        String temp = "";
        while (index < size) {
            char ch = secondLine.charAt(index);
            if (charIsOnArray(ch, whiteSpace)) {
                //just goes on.
            } else if (ch == ',') {
                listing.add(temp);
                temp = "";
            } else {
                temp = temp + ch;
            }
            index++;
        }
        listing.add(temp);
        String[] list = new String[0];
        list = listing.toArray(list);
        int[] result = new int[list.length];
        index = 0;
        while (index < list.length) {
            result[index] = getInteger(list[index]);
            index++;
        }
        return result;
    }

    /**
     * Parses a line that contains info about transitions.
     * @param transitionLine the string that has a transition
     * @return the corresponding transition
     */
    protected Transition parseTransition(String transitionLine) {
//        System.out.println("parsing line " + transitionLine);
        int index = 1, from, to;
        char ch = transitionLine.charAt(index);
        String temp = "";
        while (ch != ',') {
            temp = temp + ch;
            index++;
            ch = transitionLine.charAt(index);
        }
        from = getInteger(temp);
        temp = "";
        //Skips the space char...
        index = index + 2;
        ch = transitionLine.charAt(index);
        char ch1 = transitionLine.charAt(index + 1);
        while (!(ch == ')' && ch1 == ' ')) {
            temp = temp + ch;
            index++;
            ch = transitionLine.charAt(index);
            ch1 = transitionLine.charAt(index + 1);
        }
        String transition = temp;

        // goes more 4 positions
        index = index + 4;
        temp = "";

        while (index < transitionLine.length()) {
            ch = transitionLine.charAt(index);
            if (!charIsOnArray(ch, whiteSpace)) {
                temp = temp + ch;
            }
            index++;
        }

        to = getInteger(temp);
        Transition result;
        if (transition.charAt(0) == '"') {
            Token token;
            //TODO: add the char token here...
            if (transition.equals("\"Numero\"")) {
                token = new Token(TokenType.NUMBER, 0);
            } else if (transition.equals("\"Nome\"")) {
                token = new Token(TokenType.IDENTIFIER, -1);
            } else if (transition.equals("\"Cadeia\"")) {
                token = new Token(TokenType.CADEIA, 0);
            } else {

                String reservedWord = "";
                index = 1;
                ch = transition.charAt(index);
                while (index < transition.length() - 1) {
                    reservedWord = reservedWord + ch;
                    index++;
                    ch = transition.charAt(index);
                }

                token = new Token(TokenType.RESERVED_WORD, getIndex(reservedWord));
            }
            result = new Transition(namesList.indexOf(source.getName()), from, to, token);
        } else {
            // call to another Machine
            // or not...
            int destiny = namesList.indexOf(transition);
            if (destiny == -1) {
                Transition trans = new Transition(0, from, to, 0);
                trans.setAction(transition);
                toCorrect.add(trans);
                return null;
            } else {
                result = new Transition(namesList.indexOf(source.getName()), from, to, namesList.indexOf(transition));
            }
        }
//        LOGGER.debug("got this transition \n" + result);
        return result;
    }

    /**
     * Gets the integer from a string, note that this method
     * will return unexpected results if the string is not
     * in fact a decimal number representation
     * @param string
     * @return a integer.
     */
    protected int getInteger(String string) {
        int index = 0;
        int result = 0;
        while (index < string.length()) {
            result = result * 10 + (string.charAt(index) - 48);
            index++;
        }
        if (result + 1 > statesAmount) {
            statesAmount = result + 1;
        }
        return result;
    }

    /**
     * Based on the list of the toCorrect transition listing
     * it goes searching transitions that would lead to semantical
     * actions, that need some corrections, like the adding the action
     * and change of the next state.
     * @param transitions the transitions array, that needs corrections
     */
    protected Transition[] correctTransitions(Transition[] transitions) {

        Transition[] correctionVector = new Transition[0];
        correctionVector = toCorrect.toArray(correctionVector);
        int index = 0;
        while (index < correctionVector.length) {

            int number = correctionVector[index].getStateNumber();
            int next = correctionVector[index].getNextState();
            String action = correctionVector[index].getAction();
            int index2 = 0;
            while (index2 < transitions.length) {
                if (number == transitions[index2].getNextState()) {
                    //The transition will recieve the action..
                    transitions[index2].setNextState(next);
                    transitions[index2].setAction(action);
                }
                index2++;
            }
            index++;
        }
        return transitions;
    }
}
