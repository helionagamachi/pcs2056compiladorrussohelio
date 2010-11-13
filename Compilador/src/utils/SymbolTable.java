/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author helionagamachi
 */
public class SymbolTable {

    private int lines = 0;
    private SymbolLine firstLine;
    private SymbolLine lastLine;

    public SymbolTable() {
    }

    public SymbolLine getLine(int index) {
        if (index < 0 || index > lines) {
            return null;
        } else {
            int current = 0;
            SymbolLine currentLine = this.firstLine;
            while (current < index) {
                currentLine = currentLine.getNextLine();
                current++;
            }
            return currentLine;
        }
    }

    /**
     * Adds a line to the Symbol Table
     * @param symbol
     * @return the id received by the symbol, if the symbol was already in the
     * table, returns the id, and do not adds the it to the table
     */
    public int addLine(String symbol) {
        if (lines == 0) {
            // zero lines case , just adds the symbol on the line 0.
            this.firstLine = new SymbolLine(0, symbol);
            this.lastLine = this.firstLine;
            this.lines++;
            return 0;
        } else {
            int index = 0;
            SymbolLine compare;
            compare = this.firstLine;
            while (index < lines) {
                //checks if the symbol isn' already present on the table
                if (symbol.equals(compare.getSymbol())) {
                    //if it is, returns it index.
                    return index;
                }
                compare = compare.getNextLine();
                index++;
            }
            SymbolLine newLast = new SymbolLine(lines, symbol);
            lines++;
            this.lastLine.setNextLine(newLast);
            this.lastLine = newLast;
            return lines - 1;
        }

    }
}
