/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lex;

import static lex.ReservedWords.getByIndex;
import static utils.StringStore.stringList;

/**
 *
 * @author helionagamachi
 */
public class Token {

    private TokenType type;
    private int value;
    private int line;
    private int value2;

    /**
     * Constrói um Token com o tipo indicado e valor
     * @param type tipo do token
     * @param value valor do token , se for palavra reservada o índice da mesma,
     * numero , a parte inteira do número.
     */
    public Token(TokenType type, int value) {
        this.type = type;
        this.value = value;
    }

    public boolean compatible(Token other) {
        if (other == null) {
            return false;
        }
        if (other.type == TokenType.RESERVED_WORD) {
            return this.value == other.value;
        } else {
            return other.type == this.type;
        }
    }

    @Override
    public String toString() {
        String result = "";
        switch (type) {
            case NUMBER:
                result = "Numero " + value + "." + value2;
                break;
            case RESERVED_WORD:
                result = "Palavra reservada " + getByIndex(value);
                break;
            case IDENTIFIER:
                result = "Identificador " + value;
                break;
            case CADEIA:
                result = "Cadeia " + stringList.get(value);
                break;
            case EOF:
                result = "fim de linha ";
                break;
        }
        result += " na linha " + line;
        return result;
    }

    // <editor-fold defaultstate="collapsed" desc="GETTERS e SETTERS PADRÃO ">
    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue2() {
        return value2;
    }

    public void setValue2(int value2) {
        this.value2 = value2;
    }
// </editor-fold>
}
