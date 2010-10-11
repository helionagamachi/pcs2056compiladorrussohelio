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
    private String Symbol;
    private SymbolLine nextLine;

    public SymbolLine(int id, String Symbol) {
        this.id = id;
        this.Symbol = Symbol;
    }

    public String getSymbol() {
        return Symbol;
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
    
    

}
