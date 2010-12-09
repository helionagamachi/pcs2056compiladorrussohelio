/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package provacompiladores;

import codeGeneration.Coder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import semantic.Semantic;
import syntax.Analyzer;
import utils.CompilerException;

/**
 *
 * @author helionagamachi
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, CompilerException {

        InputStreamReader in = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(in);
            System.out.println("Caminho do arquivo:");
            String source = br.readLine();
            Coder.init("Resultado");
            Semantic.initCoder();
        try {
            File file = new File(source);
            Analyzer a = new Analyzer(file);
            a.analyze();
            Coder.getInstance().flush();
        } catch (FileNotFoundException ex) {
            System.out.println("Arquivo " + args[0] + " nao encontrado");
        } catch (IOException ex) {
           System.out.println("Erro de IO");
        }
    }

}
