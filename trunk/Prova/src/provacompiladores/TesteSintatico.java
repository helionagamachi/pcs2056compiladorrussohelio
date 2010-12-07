/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package provacompiladores;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import syntax.Analyzer;

/**
 *
 * @author helionagamachi
 */
public class TesteSintatico {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String path = TesteSintatico.class.getResource("/testeSintatico").getFile();
        File file = new File(path);
        Analyzer a = new Analyzer(file);
        a.analyze();
    }

}
