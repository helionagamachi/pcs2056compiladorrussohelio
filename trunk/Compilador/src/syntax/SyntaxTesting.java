/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package syntax;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import utils.LexicalException;

/**
 *
 * @author helionagamachi
 */
public class SyntaxTesting {

    public static void main(String[] args) throws IOException {
        Analyzer analyzer = new Analyzer();
        String[] testingFiles = {
//        "test1",
        "test2",
//        "test3",
        };
        InputStreamReader in = new InputStreamReader(System.in);
        
        for (String name : testingFiles) {
            String filePath = SyntaxTesting.class.getResource("/syntax/test/" + name).getFile();
            System.out.println("Testing syntax for the file " + name + " press enter to continue");
            in.read();
            analyzer.setFile(filePath);
            try {
                System.out.println(analyzer.analyze() ? "valid program" : "invalid program");
            } catch (LexicalException ex) {
                System.out.println(ex.getMessage());
            }
            analyzer.reset();
        }



    }
}
