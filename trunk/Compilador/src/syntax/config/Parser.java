/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package syntax.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import static utils.FileUtils.readLine;
import org.apache.log4j.Logger;
import syntax.FiniteAutomata;
import syntax.Transition;
import static utils.ArraysUtils.whiteSpaceChars;
import static utils.ArraysUtils.charIsOnArray;
import utils.StringList;

/**
 * This class is dedicated to parse the files with
 * the configuration to the finite automatas , used to syntax analysis
 * @author helionagamachi
 */
public class Parser {

    /**
     * initial: 0
    final: 2
    (0, tipo) -> 1
    (1, identificador) -> 2
    (2, ",") -> 1
     */
    // This list will be used to make equivalence on the name to the automata
    // number.
    private static StringList namesList = new StringList();
    private File source;
    private static final Logger LOGGER = Logger.getLogger(Parser.class);
    private int statesAmount;

    public Parser(File source) {
        this.source = source;
        LOGGER.debug("Preparing to parse the automata " + source.getName());
        namesList.addString(source.getName());
        this.statesAmount = 0;
    }

    public FiniteAutomata parse() {
        LOGGER.debug("Parsing the Automata");
        try {
            FileInputStream stream = new FileInputStream(source);
            //The first line I alread know, just go to the next one.
            readLine(stream);
            String secondLine = readLine(stream);
            int[] finalStates = parseSecondLine(secondLine);
            String transitionLine = readLine(stream);
            while(!transitionLine.equals("")){
                
            }
        } catch (FileNotFoundException ex) {
            //Should not happen
            LOGGER.error("An excpetion happend while reading the file " + ex.getMessage());
            return null;
        }

        return null;
    }

    /**
     * Returns the arrays with the final states numbers.
     * @param secondLine
     * @return the array containing the final states numbers .
     */
    private int[] parseSecondLine(String secondLine) {
        int size = secondLine.length();
        StringList listing = new StringList();
        int index = 6;
        String temp = "";
        while (index < size) {
            char ch = secondLine.charAt(index);
            if (charIsOnArray(ch, whiteSpaceChars)) {
                //just goes on.
                index++;
            } else if (ch == ',') {
                listing.addString(temp);
                temp = "";
                index++;
            } else {
                temp = temp + ch;
            }
        }
        String[] list = listing.getArray();
        int[] result = new int[list.length];
        index = 0;
        while (index < list.length) {
            result[index] = getInteger(temp);
            index++;
        }
        return result;
    }

    protected Transition parseTransition(String transitionLine){
        return null;
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
        if (result > statesAmount) {
            statesAmount = result;
        }
        return result;
    }
}
