package lex;

import automaton.Automata;
import automaton.CharAutomata;
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
import utils.LexicalException;
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
    private AutomataList automatas;
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
    public Token getNextToken(SymbolTable table) throws LexicalException {
//        LOGGER.debug("Automata selected " + this.selectedAutomata.name());
        Token result;
        result = null;
        while (charIsOnArray((char) now, whiteSpaceChars)) {
            readNextChar();
        }
        if (now == -1) {
            LOGGER.info("End of the file, returning the End of file token");
            Token eof = new Token(TokenType.EOF, 0);
            eof.setLine(line);
            return eof;
        }

        boolean canContinue;
        //It is ok to advance on the processing.
        canContinue = automatas.process((char) now);
        Automata finalAutomata = null;
        while (canContinue) {
            readNextChar();
            if (now == -1) {
                canContinue = false;
                // no automata understands the -1 char, so giving them a white space char.
                automatas.process('\n');
            } else {
                canContinue = automatas.process((char) now);
            }
        }
        finalAutomata = automatas.getFinalAutomata();

        if (finalAutomata == null) {
            LexicalException ex = new LexicalException("An lexical error occurred on line " + line + " with char " + (char) now);
            throw ex;
        }
        result = finalAutomata.getToken();
        if (result == null || finalAutomata.getState() == State.ERROR) {
            LexicalException ex = new LexicalException("An lexical error eccoured on line " + line + " with char " + (char) now);
            throw ex;
        }

        State automataState = finalAutomata.getState();
        if (automataState.isFinalState()) {
            //checking the state of the last automata.

            if (table != null) {
                //if I have the table to put a symbol..
                if (automataState == State.IDENTIFIER) {
                    //I have a symbol to put on the symbol table.
                    int index;
                    index = table.addLine(((WordAutomata) finalAutomata).getIdentifier());
                    result.setValue(index);
                }
            }
        }

        // Automatas need to be reseted
        automatas.resetAutomatas();
        if (result.getType() == TokenType.COMMENT) {
            //comment tokens should not be returned at all
            result = getNextToken(table);
        } else {
            //sets the line on the token
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
        this.automatas = new AutomataList();
        this.init();
    }

    /**
     * Reads the next char and stores it in the now attribute
     */
    private void readNextChar() {
        try {
            now = this.fileSource.read();
            LOGGER.debug("Got the char " + (char) now);
            if (now == '\n') {
                this.line++;
            }
        } catch (IOException ex) {
            LOGGER.error("IO exception while reading the source file" + ex.getMessage());
            now = -1;
        }

    }

    private void init() {
        Automata commentAutomata = new CommentAutomata();
        Automata numberAutomata = new NumberAutomata();
        Automata stringAutomata = new StringAutomata();
        Automata wordAutomata = new WordAutomata();
        Automata charAutomata = new CharAutomata();
        this.automatas.addAutomata(commentAutomata);
        this.automatas.addAutomata(numberAutomata);
        this.automatas.addAutomata(stringAutomata);
        this.automatas.addAutomata(wordAutomata);
        this.automatas.addAutomata(charAutomata);
        this.fileSource = null;
        this.line = 1;
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

    /**
     * For testing only, resets the instance
     */
    protected void resetAnalyzer() {
        line = 1;
        this.fileSource = null;
        this.automatas.resetAutomatas();
    }
}
