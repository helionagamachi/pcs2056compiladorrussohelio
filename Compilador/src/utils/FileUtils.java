/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author helionagamachi
 */
public class FileUtils {

    /**
     * Reads a line from the file.
     * @param file
     * @return if the file doesn't have anymore lines, returns an empty
     * string.
     */
    public static String readLine(InputStream file) {
        String result = "";
        char e;
        e = (char) read(file);
        while(e != '\n' && e != -1){
            result = result + e;
            e = (char) read(file);
        }
        return result;
    }


    private static int read(InputStream stream){
        try{
            return stream.read();
        }catch(IOException ex){
            return -1;
        }
    }
}
