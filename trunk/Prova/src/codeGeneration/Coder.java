/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package codeGeneration;

import java.io.FileWriter;
import java.io.IOException;

import utils.CompilerException;

/**
 * writes the code to the assembly form, then should call the mounter , linker , and
 * the other codes , related to the execution environment
 * @author helionagamachi
 */
public class Coder {

    private String fileName;
    private FileWriter fWriter;
    private static Coder instance;
    // will hold generated code, that has to wait a while to be put on the file.
    private String buffer;

    /**
     * Puts code on the buffer to wait a while before being written on the file
     * @param string
     * @param before the code to be buffered should be placed before or
     * after the current content?
     */
    public void putOnBuffer(String string, boolean before) {
        if (before) {
            buffer = string  + buffer;
        } else {
            buffer = buffer + string;
        }
    }

    /**
     * Private constructor, single ton pattern
     * @param fileName
     */
    private Coder(String fileName) throws CompilerException {
        this.fileName = fileName;
        buffer = "";
        try {
            this.fWriter = new FileWriter(fileName);
        } catch (IOException ex) {
            throw (new CompilerException("An error occoured while creating / writing the file!"));
        }

    }


    /**
     * Initializes the Coder , shoud be called before getting the instance.
     * @param fileName the fileName will be used for the intermediate code,
     * MVN assembly and the final code , an executable mvn file.
     */
    public static void init(String fileName) throws CompilerException {
//        System.out.println("Init called");
        if (instance == null) {
            instance = new Coder(fileName);
        }
        return;
    }

    /**
     * To get a instance, should be used only after the init method.
     * @return
     */
    public static Coder getInstance() {
//        System.out.println("coder instance");
        return instance;
    }

    /**
     * The code generation should end, flush all the contents.
     */
    public void flush() throws IOException{
//        System.out.println("The buffer had " + buffer);
        fWriter.write(buffer);
        fWriter.close();
    }
}
