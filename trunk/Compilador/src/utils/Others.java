/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import lex.Token;
import lex.TokenType;

/**
 *
 * @author helionagamachi
 */
public class Others {


    public static void printToken(Token token){
        System.out.print(token.getType().toString() + " " + token.getValue());
        if(token.getType() == TokenType.RESERVED_WORD){
            System.out.print(" - \"" + ArraysUtils.getReservedWordByIndex(token.getValue()) + "\"");
        }

        System.out.print(" - line " + token.getLine());
        System.out.println();
    }

    public static void printToken(Token token , SymbolTable table){
        System.out.print(token.getType().toString() + " " + token.getValue());
        if(token.getType() == TokenType.RESERVED_WORD){
            System.out.print(" - \"" + ArraysUtils.getReservedWordByIndex(token.getValue()) + "\"");
        }if(token.getType() == TokenType.IDENTIFIER){
            System.out.print(" - " + table.getLine(token.getValue()).getSymbol());
        }

        System.out.print(" - line " + token.getLine());
        System.out.println();
    }
}
