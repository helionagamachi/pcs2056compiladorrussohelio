/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package provacompiladores;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import lex.Analyzer;

/**
 *
 * @author helionagamachi
 */
public class TesteLexico {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String fileName = TesteLexico.class.getResource("/testeLexico").getFile();
        File file = new File(fileName);
        Analyzer analyzer = new Analyzer(file);
        System.out.println(analyzer.getNextToken());
        System.out.println(analyzer.getNextToken());
        System.out.println(analyzer.getNextToken());
        System.out.println(analyzer.getNextToken());
        System.out.println(analyzer.getNextToken());
        System.out.println(analyzer.getNextToken());
        System.out.println(analyzer.getNextToken());
        System.out.println(analyzer.getNextToken());
        System.out.println(analyzer.getNextToken());
        System.out.println(analyzer.getNextToken());
        System.out.println(analyzer.getNextToken());
        System.out.println(analyzer.getNextToken());
        System.out.println(analyzer.getNextToken());
        System.out.println(analyzer.getNextToken());
        System.out.println(analyzer.getNextToken());
        System.out.println(analyzer.getNextToken());
        System.out.println(analyzer.getNextToken());
        System.out.println(analyzer.getNextToken());
        System.out.println(analyzer.getNextToken());
        System.out.println(analyzer.getNextToken());
        System.out.println(analyzer.getNextToken());
        System.out.println(analyzer.getNextToken());
    }

}
