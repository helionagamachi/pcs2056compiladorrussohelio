package lex;

import automaton.Automata;
import automaton.CommentAutomata;
import automaton.NumberAutomata;
import automaton.State;
import automaton.StringAutomata;
import automaton.WordAutomata;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import static utils.ArraysUtils.whiteSpaceChars;
import static utils.ArraysUtils.charIsOnArray;


import org.apache.log4j.Logger;

/**
 * Class that represents the lexical analyzer
 * @author Helio
 * 
 */
public class Analyzer {

    private static Analyzer instance;
    private static Logger LOGGER = Logger.getLogger(Analyzer.class);
    private automataSelection selectedAutomata;
    private InputStream fileSource;
    private CommentAutomata commentAutomata;
    private WordAutomata wordAutomata;
    private NumberAutomata numberAutomata;
    private StringAutomata stringAutomata;
    private Automata currentAutomata;
    //Look ahead of 1?
    private int now, next;

    /**
     * Sets the file to be used by the Analyzer
     * @param filePath
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void setFile(String filePath) {
        LOGGER.info("Loading the file " + filePath);
        try {
            this.fileSource = new FileInputStream(filePath);
        } catch (FileNotFoundException ex) {
            LOGGER.error("The source file was not found!" + ex.getMessage());
        }
        readNextChar();
    }

    public Token getNextToken() {
        LOGGER.debug("Automata selected " + this.selectedAutomata.name());
        Token result;
        result = null;
        while (charIsOnArray((char) now, whiteSpaceChars)) {
            LOGGER.debug("Killing white spaces");
            readNextChar();
        }
        if (now == -1) {
            LOGGER.info("End of the file, returning the End of file token");
            return  new Token(TokenType.EOF, 0);
        }
        switch (this.selectedAutomata) {
            case NONE:
                if (this.commentAutomata.processChar((char) now)) {
                    this.selectedAutomata = automataSelection.COMMENT;
                } else if (this.stringAutomata.processChar((char) now)) {
                    this.selectedAutomata = automataSelection.STRING;
                } else if (this.numberAutomata.processChar((char) now)) {
                    this.selectedAutomata = automataSelection.NUMBER;
                } else if (this.wordAutomata.processChar((char) now)) {
                    this.selectedAutomata = automataSelection.WORD;
                }
                readNextChar();
                return getNextToken();
            case COMMENT:
                this.currentAutomata = this.commentAutomata;
                break;
            case NUMBER:
                this.currentAutomata = this.numberAutomata;
                break;
            case STRING:
                this.currentAutomata = this.stringAutomata;
                break;
            case WORD:
                this.currentAutomata = this.wordAutomata;
                break;
        }
        //advances the state machine...
        while (this.currentAutomata.processChar((char) now)) {
            //gets the next char
            readNextChar();
        }

        State automataState = this.currentAutomata.getState();
        if(automataState.isFinalState()){
            result = this.currentAutomata.getToken();
        }
        // New automata will be used from here...
        resetAutomatas();
        if (result == null){
            result = getNextToken();
        }

        return result;
    }

    /**
     * Single ton pattern.
     */
    private Analyzer() {
        this.init();
    }

    /**
     * Reads the next char and stores it in the now attribute
     */
    private void readNextChar() {
        try {
            now = this.fileSource.read();
            LOGGER.debug("Read the char : " + now);
        } catch (IOException ex) {
            LOGGER.error("IO exception while reading the source file" + ex.getMessage());
            now = -1;
        }

    }

    private void init() {
        this.selectedAutomata = automataSelection.NONE;
        this.commentAutomata = new CommentAutomata();
        this.numberAutomata = new NumberAutomata();
        this.stringAutomata = new StringAutomata();
        this.wordAutomata = new WordAutomata();
        this.currentAutomata = null;
        this.fileSource = null;
    }

    private void resetAutomatas() {
        this.commentAutomata.resetAutomata();
        this.numberAutomata.resetAutomata();
        this.stringAutomata.resetAutomata();
        this.wordAutomata.resetAutomata();
        this.selectedAutomata = automataSelection.NONE;
    }

    /**
     * Instance of the analyzer is get by this method.
     * @return The instance of the Lexical Analyzer
     */
    public static Analyzer getInstance() {
        LOGGER.debug("Analyzer instance required");
        if (instance == null) {
            instance = new Analyzer();
        }
        return instance;
    }

    /**
     * For testing only, resets the instance
     */
    protected void resetAnalyzer()  {
        instance = new Analyzer();
    }

    protected enum automataSelection {

        COMMENT, NUMBER, STRING, WORD, NONE
    }
}
