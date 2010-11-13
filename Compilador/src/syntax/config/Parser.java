/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package syntax.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import lex.Token;
import lex.TokenType;
import static utils.FileUtils.readLine;
import org.apache.log4j.Logger;
import syntax.FiniteAutomata;
import syntax.Transition;
import utils.ArraysUtils;
import static utils.ArraysUtils.whiteSpaceChars;
import static utils.ArraysUtils.charIsOnArray;
import utils.StringListing;
import utils.TransitionList;

/**
 * This class is dedicated to parse the files with
 * the configuration to the finite automatas , used to syntax analysis
 * @author helionagamachi
 */
public class Parser {

    // This list will be used to make equivalence on the name to the automata
    // number.
    protected static StringListing namesList = new StringListing();
    private File source;
    private static final Logger LOGGER = Logger.getLogger(Parser.class);
    private int statesAmount;

    public Parser(File source) {
        this.source = source;
        LOGGER.debug("Preparing to parse the automata " + source.getName());
        addString(source.getName());
        this.statesAmount = 0;

    }

    public FiniteAutomata parse() {
        LOGGER.debug("Parsing the Automata");
        this.statesAmount = 0;
        try {
            FileInputStream stream = new FileInputStream(source);
            //The first line I alread know, just go to the next one.
            readLine(stream);
            String secondLine = readLine(stream);
            int[] finalStates = parseSecondLine(secondLine);
            String transitionLine = readLine(stream);
            TransitionList transList = new TransitionList();
            while (!transitionLine.equals("")) {
                transList.addTransition(parseTransition(transitionLine));
                transitionLine = readLine(stream);
            }
            Transition[] transitions = transList.getArray();
            FiniteAutomata automata = new FiniteAutomata(statesAmount, transitions.length, namesList.getId(source.getName()));
            automata.initStates(finalStates);
            automata.setTransitions(transitions);
            return automata;

        } catch (FileNotFoundException ex) {
            //Should not happen
            LOGGER.error("An excpetion happend while reading the file " + ex.getMessage());
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
        StringListing listing = new StringListing();
        int index = 6;
        String temp = "";
        while (index < size) {
            char ch = secondLine.charAt(index);
            if (charIsOnArray(ch, whiteSpaceChars)) {
                //just goes on.
            } else if (ch == ',') {
                listing.addString(temp);
                temp = "";
            } else {
                temp = temp + ch;
            }
            index++;
        }
        listing.addString(temp);
        String[] list = listing.getArray();
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
        while (ch != ')') {
            temp = temp + ch;
            index++;
            ch = transitionLine.charAt(index);
        }
        String transition = temp;

        // goes more 4 positions
        index = index + 4;
        temp = "";

        while (index < transitionLine.length()) {
            ch = transitionLine.charAt(index);
            if (!charIsOnArray(ch, whiteSpaceChars)) {
                temp = temp + ch;
            }
            index++;
        }

        to = getInteger(temp);
        Transition result;
        System.out.println(transition);
        if (transition.charAt(0) == '"') {
            Token token;
            if (transition.equals("\"number\"")) {
                token = new Token(TokenType.INT, 0);
            }else if(transition.equals("\"identificador\"")){
                token = new Token(TokenType.IDENTIFIER, -1);
            }
            else {
                String reservedWord = "";
                index = 1;
                ch = transition.charAt(index);
                while (index < transition.length() - 1) {
                    reservedWord = reservedWord + ch;
                    index++;
                    ch = transition.charAt(index);
                }
                
                token = new Token(TokenType.RESERVED_WORD, ArraysUtils.getReservedWordIndex(reservedWord));
            }
            result = new Transition(namesList.getId(source.getName()), from, to, token);
        } else {
            // call to another Machine
            addString(transition);
            result = new Transition(namesList.getId(source.getName()), from, to, namesList.getId(transition));
        }
        
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
        if (result+1 > statesAmount) {
            statesAmount = result + 1;
        }
        return result;
    }

    /**
     * Adds the string on the list, only if it is not
     * already on the list.
     * @param string the string to be added, or not.
     */
    private void addString(String string) {
        if (namesList.getId(string) == -1) {
            namesList.addString(string);
        }
    }
}
