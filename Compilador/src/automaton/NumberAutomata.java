/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package automaton;

import lex.Token;
import lex.TokenType;

/**
 * Automata to deal with the numbers, float and integers
 * @author Helio
 */
public class NumberAutomata extends Automata {

    private int integerNumber;
    private float floatNumber;
    private int afterDot;

    public NumberAutomata() {
        this.integerNumber = 0;
        this.afterDot = 0;
        this.floatNumber = (float) 0.0;
        this.currentState = State.INITIAL;
    }

    @Override
    public boolean processChar(char a) {
        boolean result = false;
        switch (this.currentState) {
            case INITIAL:
                if (isNumber(a)) {
                    appendInteger(a);
                    result = true;
                    this.currentState = State.INTEGER;
                } else if (a == '.') {
                    result = true;
                    this.currentState = State.FLOAT_STARTED_BY_DOT;
                }
                break;
            case INTEGER:
                if (isNumber(a)) {
                    //Nothing changes
                    appendInteger(a);
                    result = true;
                } else if (a == '.') {
                    result = true;
                    this.floatNumber = this.integerNumber;
                    //Not a integer, but a float
                    this.currentState = State.FLOAT;
                }
                break;
            case FLOAT_STARTED_BY_DOT:
                if (isNumber(a)) {
                    result = true;
                    appendFloat(a);
                    this.currentState = State.FLOAT;
                }
                break;
            case FLOAT:
                if (isNumber(a)) {
                    result = true;
                    appendFloat(a);
                }
                break;
        }
        return result;
    }

    @Override
    public Token getToken() {
        //The MVN will not get a Float , so the result will be the integer part
        Token result = new Token(TokenType.INT, this.integerNumber);
        return result;

    }

    @Override
    public void resetAutomata() {
        this.integerNumber = 0;
        this.afterDot = 0;
        this.floatNumber = (float) 0.0;
        this.currentState = State.INITIAL;
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

    /**
     * Appends a char to the integer property
     * @param a the char to be appended
     */
    private void appendInteger(char a) {
        this.integerNumber = this.integerNumber * 10 + charToNumber(a);
    }
   

    /**
     * Checks if the given character is a
     * @param a the char to be appended
     */
    private void appendFloat(char a) {
        this.afterDot++;
        float temp = charToNumber(a);
        for (int count = 0; count < afterDot; count++) {
            temp = temp / 10;
        }
        this.floatNumber = this.floatNumber + temp;
    }

    /**
     * Converts a character to a number, in fact subtracts 0x30 from the byte
     * representation
     * @param a the character to be converted
     * @return the number.
     */
    protected int charToNumber(char a) {
        return a - 0x30;
    }

    //Tests and debug
    protected float getFloatNumber() {
        return floatNumber;
    }
    //Tests and debug
    protected int getIntegerNumber() {
        return integerNumber;
    }



}
