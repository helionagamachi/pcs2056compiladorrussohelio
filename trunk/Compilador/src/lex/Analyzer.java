package lex;

import automaton.State;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
/**
 * Class that represents the lexical analyzer
 * @author Helio
 * 
 */
public class Analyzer {

    private static Analyzer instance;
    private static Logger LOGGER = Logger.getLogger(Analyzer.class);
    private State stateMachine;
    private InputStream fileSource;
    //Look ahead of 1
    private int now , next;
    
    public void setStateMachine(State stateMachine) {
        this.stateMachine = stateMachine;
    }

    /**
     * Sets the file to be used by the Analyzer
     * @param filePath
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void setFile(String filePath) throws FileNotFoundException, IOException{
        LOGGER.info("Loading the file " + filePath);
        this.fileSource = new FileInputStream(filePath);
        this.now = this.fileSource.read();
        

    }

    public Token getNextToken(){
        Token result;
        result = null;
        if(this.now==-1){
            
        }
        

        return new Token(TokenType.INT, 9);
    }

    /**
     * Single ton pattern.
     */
    private Analyzer() {

    }
    
    public static Analyzer getInstance(){
        LOGGER.debug("Analyzer instance required");
        if(instance == null){
            instance = new Analyzer();
        }
        return instance;
    }


}
