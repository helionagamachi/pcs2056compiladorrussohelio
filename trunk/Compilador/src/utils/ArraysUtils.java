/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 * This class offers some arrays and functions that can be used by others.
 * @author helionagamachi
 */
public class ArraysUtils {

    /**
     * All reserved words that does not takes at least letter
     */
    public static final String[] operators = {
        "+",
        "-",
        "++",
        ")",
        "(",
        "[",
        "]",
        "=",
        "+=",
        ";",
    };

    public static final String[] reservedWords = {
        "if",
        "while",
        "for",
        "int",
        "float",
        "else",
    };

    public static final char[] whiteSpaceChars = {
        ' ', '\t', '\n' , '\r',
    };


    public static final char[] letters = {
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
        'p', 'q', 'r', 's', 't', 'u', 'v', 'x', 'w', 'y', 'z',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
        'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'X', 'W', 'Y', 'Z',};


    /**
     * Checks if the given char is present in the char array
     * @param check the char that must be found
     * @param charArray the array where it will look
     * @return true, if the char is on the array.
     */
    public static boolean charIsOnArray(char check, char[] charArray) {
        boolean result = false;
        int total = charArray.length;
        int count = 0;
        while (count < total) {
            if (check == charArray[count]) {
                result = true;
                break;
            }
            count++;
        }
        return result;
    }

}
