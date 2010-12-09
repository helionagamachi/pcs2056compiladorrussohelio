/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Main;

import codeGeneration.Coder;
import codeGeneration.Mount;
import java.io.BufferedReader;
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
    public static void main(String[] args) throws IOException{
        try {
            InputStreamReader in = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(in);
            System.out.println("Where is the source code?");
            String source = br.readLine();
            Coder.init(source);
            Semantic.initCoder();
            Analyzer analyzer = new Analyzer();
            analyzer.setFile(source);
            if (analyzer.analyze()) {
                String FinalString = "# FIM";
                Coder.getInstance().putOnBuffer(FinalString, false);
                Coder.getInstance().flush();
                System.out.println("Valid code, calling the mounter");
                Mount.Mount(source+"_interm.asm");
                System.out.println("Linking");
                Mount.Link(source+"_interm.asm");
                System.out.println("Relocating");
                Mount.reloc();
            }
        } catch (CompilerException ex) {
            System.out.println(ex.getMessage());
        }
        
    }

}
