/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package codeGeneration;

import utils.CompilerException;

/**
 *
 * @author helionagamachi
 */
public class CoderTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws CompilerException {
        Coder.init("Teste");
        Coder coder = Coder.getInstance();
        
    }

}
