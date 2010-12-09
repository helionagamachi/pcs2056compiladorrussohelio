/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package provacompiladores;

import codeGeneration.Coder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import syntax.Analyzer;
import utils.CompilerException;

/**
 *
 * @author helionagamachi
 */
public class TesteSemantico {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, CompilerException {
        String path = TesteSintatico.class.getResource("/testeSemantico").getFile();
        codeGeneration.Coder.init("teste");
        semantic.Semantic.initCoder();
        File file = new File(path);
        Analyzer a = new Analyzer(file);
        a.analyze();
        Coder.getInstance().flush();
    }

}
