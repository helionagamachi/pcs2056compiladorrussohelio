/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package syntax;

import codeGeneration.Coder;
import java.io.IOException;
import java.io.InputStreamReader;
import semantic.Semantic;
import utils.CompilerException;
import utils.LexicalException;

/**
 *
 * @author helionagamachi
 */
public class SyntaxTesting {

    public static void main(String[] args) throws IOException, CompilerException {
        Analyzer analyzer = new Analyzer();
        Coder.init("Teste");
        Semantic.initCoder();
        String[] testingFiles = {
//        "test1",
//        "test2",
//        "test3",
        "test4",
        };
        InputStreamReader in = new InputStreamReader(System.in);
        
        for (String name : testingFiles) {
            String filePath = SyntaxTesting.class.getResource("/syntax/test/" + name).getFile();
            System.out.println("Testing syntax for the file " + name + " press enter to continue");
            in.read();
            analyzer.setFile(filePath);
            try {
                System.out.println(analyzer.analyze() ? "valid program" : "invalid program");
                Coder.getInstance().flush();
            } catch (LexicalException ex) {
                System.out.println(ex.getMessage());
            }
            analyzer.reset();
        }



    }
}
