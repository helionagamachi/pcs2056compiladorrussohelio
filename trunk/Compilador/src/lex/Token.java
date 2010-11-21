package lex;

import utils.ArraysUtils;

/**
 * Represents a token
 * @author helionagamachi
 */
public class Token {

    private TokenType type;
    private int value;
    private int line;

    /**
     * Creates a new token
     * @param type the type of the token, see
     * @param value an Integer Value to the Token
     */
    public Token(TokenType type, int value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Returns the type of the token
     * @return
     */
    public TokenType getType() {
        return type;
    }

    /**
     * Get the value of the token
     * @return
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the value of the token
     * @param value the integer value to be the new Token Value
     */
    public void setValue(int value) {
        this.value = value;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public boolean compatible(Token other) {
        TokenType instanceType = this.type;
        switch (instanceType) {
            case RESERVED_WORD:
                boolean sameType;
                boolean sameWord;
                sameType = other.type == TokenType.RESERVED_WORD;
                sameWord = this.value == other.value;
                return sameType && sameWord;
            case IDENTIFIER:
                return other.getType() == TokenType.IDENTIFIER;
            case INT:
                return other.getType() == TokenType.INT;
            case STRING:
                return other.getType() == TokenType.STRING;
            default:
                return false;

        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Token)) {
            return false;
        } else {
            boolean sameType;
            boolean sameValue;
            boolean sameLine;
            Token obj = (Token) o;
            sameType = this.type == obj.type;
            sameValue = this.value == obj.value;
            sameLine = this.line == obj.line;
            return sameType && sameValue && sameLine;
        }
    }

    @Override
    public String toString() {
        if (type == TokenType.RESERVED_WORD) {
            if (value > 0) {
                return this.type.name() + " " + ArraysUtils.getReservedWordByIndex(this.value) + " on line " + this.line;
            } else {
                return this.type.name() + " " + this.value + " on line " + this.line;
            }
        }
        return this.type.name() + " " + this.value + " on line " + this.line;
    }
}
