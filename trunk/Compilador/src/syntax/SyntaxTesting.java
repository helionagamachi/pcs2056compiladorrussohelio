/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package syntax;

import java.util.logging.Level;
import java.util.logging.Logger;
import utils.LexicalException;

/**
 *
 * @author helionagamachi
 */
public class SyntaxTesting {


    public static void main(String[] args) {
        Analyzer analyzer = new Analyzer();
        String filePath = SyntaxTesting.class.getResource("/syntax/test/test1").getFile();
        analyzer.setFile(filePath);
        try {
            System.out.println(analyzer.analyze());
        } catch (LexicalException ex) {
            System.out.println(ex.getMessage());
        }
        
    }


}
