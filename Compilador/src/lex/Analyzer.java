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
import utils.SymbolTable;

/**
 * Class that represents the lexical analyzer
 * @author Helio
 * 
 */
public class Analyzer {

    private static Analyzer instance;
    private static Logger LOGGER = Logger.getLogger(Analyzer.class);
    private InputStream fileSource;
    private CommentAutomata commentAutomata;
    private WordAutomata wordAutomata;
    private NumberAutomata numberAutomata;
    private StringAutomata stringAutomata;
    protected Automata possibleAutomatas[];
    //Look ahead of 1?
    private int now, line;

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

    /**
     * Gets the next token on from the source file
     * @return the token, if reaches the end of the file, token of the type EOF
     * will be given
     */
    public Token getNextToken(SymbolTable table) {
//        LOGGER.debug("Automata selected " + this.selectedAutomata.name());
        Token result;
        result = null;
        while (charIsOnArray((char) now, whiteSpaceChars)) {
//            LOGGER.debug("Killing white spaces");
            readNextChar();
        }
        if (now == -1) {
            LOGGER.info("End of the file, returning the End of file token");
            Token eof = new Token(TokenType.EOF, 0);
            eof.setLine(line);
            return eof;
        }
        if (this.possibleAutomatas.length == 0) {
            //Gathers the possible automatas.
            if (this.commentAutomata.processChar((char) now)) {
                addAutomata2List(commentAutomata);
            }
            if (this.stringAutomata.processChar((char) now)) {
                addAutomata2List(stringAutomata);
            }
            if (this.numberAutomata.processChar((char) now)) {
                addAutomata2List(numberAutomata);
            }
            if (this.wordAutomata.processChar((char) now)) {
                addAutomata2List(wordAutomata);
            }
//            readNextChar();
//            return getNextToken(table);
        }
        //It is ok to advance on the processing.
        boolean canContinue = true;

        Automata analyzedAutomata = null;
        while (canContinue) {
            readNextChar();
            int index = 0;
            boolean toBeRemoved[] = new boolean[possibleAutomatas.length];
            canContinue = false;
            boolean noFinalStateAutomata = true;
            //loops the array of possible Automatas.
//            LOGGER.debug("working with " + possibleAutomatas.length + " possible Automata");
            while (index < possibleAutomatas.length && noFinalStateAutomata) {
                analyzedAutomata = possibleAutomatas[index];
                if (analyzedAutomata.processChar((char) now)) {
                    canContinue = true;
                    toBeRemoved[index] = false;
                } else {
                    State state = analyzedAutomata.getState();
                    if (state.isFinalState()) {
                        // can stop.
                        noFinalStateAutomata = false;
                        canContinue = false;
                    } else {
                        toBeRemoved[index] = true;
                    }
                }
                index++;
            }
            if (canContinue) {
                removeAutomatas(toBeRemoved);
            }
        }
        if (analyzedAutomata != null) {
            State automataState = analyzedAutomata.getState();
            if (automataState.isFinalState()) {
                
                result = analyzedAutomata.getToken();
                if(result==null){
                    LOGGER.debug("null result, read another char..");
                    readNextChar();
                }
                if (table != null) {
                    if (automataState == State.IDENTIFIER) {
                        int index;
                        index = table.addLine(this.wordAutomata.getIdentifier());
                        result.setValue(index);
                    }
                }
            }
        }
        // Automata need to be reseted
        resetAutomatas();
        if (result == null) {
            result = getNextToken(table);
        } else {
            if (now == '\n') {
                result.setLine(line - 1);
            } else {
                result.setLine(this.line);
            }
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
//            LOGGER.debug("Read the char " + (char) now);
            if (now == '\n') {
                this.line++;
            }
//            LOGGER.debug("Read the char : " + now);
        } catch (IOException ex) {
            LOGGER.error("IO exception while reading the source file" + ex.getMessage());
            now = -1;
        }

    }

    private void init() {
//        this.selectedAutomata = automataSelection.NONE;
        this.commentAutomata = new CommentAutomata();
        this.numberAutomata = new NumberAutomata();
        this.stringAutomata = new StringAutomata();
        this.wordAutomata = new WordAutomata();
        this.fileSource = null;
        this.line = 1;
        this.possibleAutomatas = new Automata[0];
    }

    private void resetAutomatas() {
        LOGGER.debug("Reseted the automatas");
        this.commentAutomata.resetAutomata();
        this.numberAutomata.resetAutomata();
        this.stringAutomata.resetAutomata();
        this.wordAutomata.resetAutomata();
        this.possibleAutomatas = new Automata[0];
//        this.selectedAutomata = automataSelection.NONE;
    }

    /**
     * Instance of the analyzer is get by this method.
     * @return The instance of the Lexical Analyzer
     */
    public static Analyzer getInstance() {
        LOGGER.debug("Lexical Analyzer instance required");
        if (instance == null) {
            instance = new Analyzer();
        }
        return instance;
    }

    protected void removeAutomatas(boolean toBeRemoved[]) {
        int amount2Remove, index;
        amount2Remove = 0;
        index = 0;
        while (index < toBeRemoved.length) {
            if (toBeRemoved[index]) {
                amount2Remove++;
            }
            index++;
        }
        index = 0;
        Automata newArray[] = new Automata[possibleAutomatas.length - amount2Remove];
        int index2 = 0;
        while (index < possibleAutomatas.length) {
            if (!toBeRemoved[index]) {
                newArray[index2] = possibleAutomatas[index];
                index2++;
            }
            index++;
        }
        this.possibleAutomatas = newArray;

    }

    protected void addAutomata2List(Automata automaton) {
        int size = this.possibleAutomatas.length + 1;
        Automata newArray[] = new Automata[size];
        int index = 0;
        while (index < size - 1) {
            newArray[index] = possibleAutomatas[index];
            index++;
        }
        newArray[size - 1] = automaton;
        this.possibleAutomatas = newArray;
    }

    /**
     * For testing only, resets the instance
     */
    protected void resetAnalyzer() {
        this.init();
    }
}
