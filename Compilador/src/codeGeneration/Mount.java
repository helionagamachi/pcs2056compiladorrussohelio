/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package codeGeneration;

import montador.MvnAsm;

/**
 *
 * @author helionagamachi
 */
public class Mount {

    private static String[] files = {"and.asm" , "diferente.asm"};

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String[] args2 ;
        args2 = getFilePaths();


        MvnAsm.main(args2);
    }


       private static String[] getFilePaths(){
        String[] result = new String[files.length];
        int index = 0;
        while(index < files.length){
            result[index] = Mount.class.getResource("/codeGeneration/executionEnvironment/" + files[index]).getFile();
            index ++;
        }
        return result;
    }

}
