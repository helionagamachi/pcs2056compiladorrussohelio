/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package automaton;

import lex.Token;
import org.apache.log4j.Logger;

/**
 * Automata to find reserved words and identifiers,
 * eg.: function names and variable names
 * @author Helio
 */
public class WordAutomata extends Automata {

    private static Logger LOGGER = Logger.getLogger(WordAutomata.class);
    private final char[] letters = {
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
        'p', 'q', 'r', 's', 't', 'u', 'v', 'x', 'w', 'y', 'z',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
        'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'X', 'W', 'Y', 'Z',};
    private final char[] whiteSpaceChars = {
        ' ', '\t', '\n'
    };
    private String name;
    private final String[] reservedWords = {
        "if",
        "while",
        "for",
        "int",
        "float",};
    private final String[] operators = {
        "+",
        "-",
        "++",
        ")",
        "(",
        "[",
        "]",
        "=",
        "+=",};
    private String possibleReserved[];

    public WordAutomata() {
        this.name = "";
        this.currentState = State.INITIAL;
        this.possibleReserved = new String[0];
    }

    @Override
    public boolean processChar(char a) {

        boolean result = false;
        switch (this.currentState) {
            case INITIAL:
                checkPossibleReserved(a);
                if (this.possibleReserved.length == 0) {
                    // no possible reserved, word, it is an identifier.
                    if (charIsOnArray(a, letters)) {
                        //The first char on a identifier must be a letter
                        this.currentState = State.IDENTIFIER;
                        this.name += a;
                        result = true;
                    }
                } else {
                    if (isInitialOperator(a)) {
                        //likely to be an operator
                        this.currentState = State.POSSIBLE_OPERATOR;
                    } else {
                        //has a chance to be a reserved word
                        this.currentState = State.POSSIBLE_RESERVED_WORD;
                    }
                    result = true;
                    this.name += a;
                }
                break;
            case IDENTIFIER:
                if (isNumber(a) || charIsOnArray(a, letters)) {
                    //Still a identifier
                    result = true;
                    this.name += a;
                }
                break;
            case POSSIBLE_RESERVED_WORD:
                LOGGER.debug("On the possible reserved word state");
                if (isInitialOperator(a) || charIsOnArray(a, whiteSpaceChars)) {
                    //very likely that it is the end...
//                    if (this.possibleReserved.length > 0) {
                    int index = -1, count = 0;
                    while (count < this.possibleReserved.length) {
                        if (this.name.equals(this.possibleReserved[count])) {
                            index = count;
                            break;
                        }
                        count++;
                    }
                    if (index != -1) {
                        // Found a reserverd word!
                        this.currentState = State.RESERVED_WORD;
                    } else {
                        // could be an identifier with like
                        // whil , right after it a white space or possible operator
                        this.currentState = State.IDENTIFIER;
                    }
//                    }

                } else {
                    checkPossibleReserved(a);
                    if (this.possibleReserved.length == 0) {
                        // no possible reserved word, it is a identifier like
                        //ifNot
                        this.currentState = State.IDENTIFIER;
                    }
                    result = true;
                    this.name += a;
                }
                break;
            case POSSIBLE_OPERATOR:
                if (this.possibleReserved.length == 1) {
                    // don't even want to check, just make it return, analyzer
                    // should grab the token
                    this.currentState = State.RESERVED_WORD;
                } else {
                    checkPossibleReserved(a);
                    //it reduced the possibilities to zero!
                    if (this.possibleReserved.length == 0) {
                        LOGGER.error("Word Automata in error State!");
                        this.currentState = State.ERROR;
                    } else {
                        this.name += a;
                        result = true;
                    }
                }
                break;
            case RESERVED_WORD:
                //doesn't consume the char
                break;

        }
        return result;
    }

    @Override
    public Token getToken() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void resetAutomata() {
        this.name = "";
        this.currentState = State.INITIAL;
        this.possibleReserved = new String[0];
    }

    //checks if a character is present in a array of characters
    protected boolean charIsOnArray(char check, char[] charArray) {
        boolean result = false;
        int total = charArray.length;
        int count = 0;
        while (count < total) {
            if (check == charArray[count]) {
                result = true;
                break;
            }
            count++;
        }
        return result;
    }

    //checks for possible reserverd words that can matche the char
    private void checkPossibleReserved(char check) {
        LOGGER.debug("Checking possible reserved words");
        if (this.possibleReserved.length == 0) {
            // <editor-fold defaultstate="collapsed" desc="Checking for possible ones.">
            LOGGER.debug("Should be the first check this one");
            // its is likely to be the first time to be run.
            int possibleQuantity = 0;
            for (int count = 0; count < this.reservedWords.length; count++) {
                if (check == this.reservedWords[count].charAt(0)) {
                    possibleQuantity++;
                }
            }
            for (int count = 0; count < this.operators.length; count++) {
                if (check == this.operators[count].charAt(0)) {
                    possibleQuantity++;
                }
            }
            LOGGER.debug("The possible quantity of reserved words is :" + possibleQuantity);
            if (possibleQuantity > 0) {
                this.possibleReserved = new String[possibleQuantity];
            } else {
                LOGGER.debug("Ended the search for reserved words");
                return;
            }
            int done = 0;
            for (int count = 0; count < this.reservedWords.length; count++) {
                if (check == this.reservedWords[count].charAt(0)) {
                    this.possibleReserved[done] = this.reservedWords[count];
                    done++;
                }
            }
            for (int count = 0; count < this.operators.length; count++) {
                if (check == this.operators[count].charAt(0)) {
                    this.possibleReserved[done] = this.operators[count];
                    done++;
                }
            }
// </editor-fold>
        } else {
            // The possible reserved word array has at least one element.
            // I have the string so far collected.
            // This new char will be on wich index ?
            // Compare with the char on this index on each candidate.
            LOGGER.debug("Not the first check for reserved words");
            //a temp array, it eventually will substitute the current array
            String[] temp = new String[this.possibleReserved.length];
            //The length of the stored string indicates where to look
            int index2Compare = this.name.length();
            int found = 0;
            for (int count = 0; count < this.possibleReserved.length; count++) {
                if (this.possibleReserved[count].length() > index2Compare) {
                    //Avoiding index outbounds exception
                    if (check == this.possibleReserved[count].charAt(index2Compare)) {
                        //copies to the temp array.
                        temp[found] = this.possibleReserved[count];
                        found++;
                    }

                }

            }
            if (found < this.possibleReserved.length) {
                // a new array is necessary.
                String[] temp2 = new String[found];
                int count = 0;
                while (count < found) {
                    temp2[count] = temp[count];
                    count++;
                }
                temp = temp2.clone();
            }

            this.possibleReserved = temp;

        }
        LOGGER.debug("Ended the search for reserved words");

    }

    /**
     * Checks if the character is the initial char of a operator string
     * @param check the character to check
     * @return if it is , or not the initial char of a symbol string
     */
    protected boolean isInitialOperator(char check) {
        boolean result = false;
        int count = 0;
        int total = this.operators.length;
        while (count < total) {
            if (check == this.operators[count].charAt(0)) {
                result = true;
                break;
            }
            count++;
        }
        return result;
    }

    protected String getName() {
        return this.name;
    }

    /**
     * Checks if the given character is a number
     * @param a the character to check
     * @return true if it is a number, false eitherwise
     */
    private boolean isNumber(char a) {
        return a == '0' || a == '1' || a == '2' || a == '3' || a == '4'
                || a == '5' || a == '6' || a == '7' || a == '8' || a == '9';
    }
}
