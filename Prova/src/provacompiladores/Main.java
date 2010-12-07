/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package provacompiladores;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import syntax.Analyzer;

/**
 *
 * @author helionagamachi
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            File file = new File(args[0]);
            Analyzer a = new Analyzer(file);
            a.analyze();
        } catch (FileNotFoundException ex) {
            System.out.println("Arquivo " + args[0] + " nao encontrado");
        } catch (IOException ex) {
           System.out.println("Erro de IO");
        }
    }

}
