/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 * Classe com funcionalidades úteis com arrays , e
 * alguns arrays úteis.
 * @author helionagamachi
 */
public class ArrayUtils {
    /**
     * Letras
     */
    public static final char[] letters = {
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
        'p', 'q', 'r', 's', 't', 'u', 'v', 'x', 'w', 'y', 'z',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
        'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'X', 'W', 'Y', 'Z',};
    public static final char[] numbers = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    // caracteres que denotam espaços em branco...
    public static final char[] whiteSpace = {'\n', '\r', ' ', '\t'};

    /**
     * Checks if the given char is present in the char array
     * @param check the char that must be found
     * @param charArray the array where it will look
     * @return true, if the char is on the array.
     */
    public static boolean charIsOnArray(char check, char[] charArray) {
        boolean result = false;
        int total = charArray.length;
        int counter = 0;
        while (counter < total) {
            if (check == charArray[counter]) {
                result = true;
                break;
            }
            counter++;
        }
        return result;
    }
}
