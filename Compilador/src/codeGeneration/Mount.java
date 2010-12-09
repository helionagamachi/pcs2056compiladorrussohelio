/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package codeGeneration;

import linker.MvnLinker;
import montador.MvnAsm;
import relocator.MvnRelocator;

/**
 *
 * @author helionagamachi
 */
public class Mount {

    private static String[] files = {"and.mvn" , "diferente.mvn" , "igual.mvn" , "maior.mvn" ,
    "maior_igual.mvn", "menor.mvn" , "menor_igual.mvn" , "not.mvn" , "ou.mvn" , "print.mvn" , "printnum.mvn"};

    
 public static void Mount(String file){
        String[] args;
        args = new String[1];
        args[0] = file;
        MvnAsm.main(args);
    }

 public static void Link(String file){
     String[] args;
     args = getFilePaths(file);
     MvnLinker.main(args);
 }

 public static void reloc(){
     String[] args = {"linked.mvn" , "final.mvn" , "0"} ;
     MvnRelocator.main(args);
 }

       private static String[] getFilePaths(String file){

        String[] result = new String[files.length + 3];
        result[0] = file.replace("asm", "mvn");
        int index = 0;
        while(index < files.length){
            result[index+1] = Mount.class.getResource("/codeGeneration/executionEnvironment/mvn/" + files[index]).getFile();
            index ++;
        }
        result[result.length-2] = "-s";
        result[result.length-1] = "linked.mvn";

        return result;
    }

}
