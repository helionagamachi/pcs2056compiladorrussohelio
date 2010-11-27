/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

/**
 *
 * @author helionagamachi
 */
public class SymbolLine {
    private int id;
    private String symbol;
    private SymbolLine nextLine;
    private SymbolType type;
    private int dimension;

    public SymbolLine(int id, String Symbol) {
        this.id = id;
        this.symbol = Symbol;
        this.type = SymbolType.UNKOWN;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getId() {
        return id;
    }

    public SymbolLine getNextLine() {
        return nextLine;
    }

    public void setNextLine(SymbolLine nextLine) {
        this.nextLine = nextLine;
    }

    public SymbolType getType() {
        return type;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public void setType(SymbolType type) {
        this.type = type;
    }
    
    
}
