/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

/**
 * This code is destined to help with scape chars.
 * @author helionagamachi
 */
public class EscapeChars {


    /**
     * Escapes a char , likely, if you provides n, it should return \n
     * t , \t and \ should return \\ , to be used on lexical automatas.
     * @param c
     * @return
     */
    public static char escapeChar(char c){
        char result = ' ';
        switch(c){
            case 'n':
                result = '\n';
                break;
            case 't':
                result = '\t';
                break;
            case 'r':
                result = '\r';
                break;
            case '\\':
                result = '\\';
                break;
            case '\'':
                result = '\'';
                break;
            case '"':
                result = '"';
                break;
        }
        return result;
    }

}
