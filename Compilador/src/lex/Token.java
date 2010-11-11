package lex;

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
                return this.equals(other);
            case IDENTIFIER:
                return other.getType() == TokenType.IDENTIFIER;
            case INT:
                return other.getType() == TokenType.INT;
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
            Token obj = (Token) o;
            sameType = this.type == obj.type;
            sameValue = this.value == obj.value;
            return sameType && sameValue;
        }
    }

    @Override
    public String toString() {
        return this.type.name() + " " + this.value;
    }
}
