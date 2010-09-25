package lex;

/**
 * Represents a token
 * @author helionagamachi
 */
public class Token {

    private TokenType type;
    private int value;

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


    @Override
    public String toString(){
        return this.type.name() + " " + this.value ;
    }
}
