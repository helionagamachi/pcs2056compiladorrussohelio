package lex;

import java.io.File;

import org.apache.log4j.Logger;

/**
 * Class that represents the lexical analyzer
 * @author Helio
 * 
 */
public class Analyzer {

    private static Logger LOGGER = Logger.getLogger(Analyzer.class);
    private File sourceFile;

    public Analyzer(String sourceFilePath) {
        LOGGER.info("Recieved " + sourceFilePath + " as the source file");
        this.sourceFile = new File(sourceFilePath);
        if (!this.sourceFile.exists()) {
            LOGGER.fatal("The provided file does not exists");
        }
    }
}
