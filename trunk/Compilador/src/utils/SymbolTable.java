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


    public SymbolLine getLine(int index){
        if(index < 1 || index > lines){
            return null;
        }else{
            int current = 1;
            SymbolLine currentLine = this.firstLine;
            while(current < index){
                currentLine = currentLine.getNextLine();
                current ++;
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
            this.firstLine = new SymbolLine(1, symbol);
            this.lastLine = this.firstLine;
            this.lines++;
            return 1;
        } else if (lines == 1) {
            if (symbol.equals(this.firstLine.getSymbol())) {
                return 1;
            } else {
                SymbolLine last = new SymbolLine(2, symbol);
                this.firstLine.setNextLine(last);
                this.lastLine = last;
                lines++;
                return 2;
            }
        } else {
            int index = 1;
            SymbolLine compare;
            compare = this.firstLine;
            if (symbol.equals(compare.getSymbol())) {
                return index;
            }
            index++;
            while (index <= lines) {
                compare = compare.getNextLine();
                if (symbol.equals(compare.getSymbol())) {
                    return index;
                }
                index++;
            }
            lines++;
            SymbolLine newLast = new SymbolLine(lines, symbol);
            this.lastLine.setNextLine(newLast);
            this.lastLine = newLast;
            return lines;
        }

    }
}
