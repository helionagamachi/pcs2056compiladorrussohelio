package lex;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import lex.automaton.Automaton;
import lex.automaton.NumberAutomaton;
import lex.automaton.StringAutomaton;
import lex.automaton.WordAutomaton;
import static utils.ArrayUtils.whiteSpace;
import static utils.ArrayUtils.charIsOnArray;

public class Analyzer {

    private ArrayList<Automaton> automatas = new ArrayList<Automaton>();
    private FileReader fileReader;
    private int line;
    private int now;

    /**
     * Cria uma instancia do analizador lexico
     */
    public Analyzer(File file) throws FileNotFoundException, IOException {
        initAutomatas();
        this.fileReader = new FileReader(file);
        line = 1;
        now = 0;
        readNextChar();
    }

    /**
     * Retorna o proximo token do arquivo.
     * @return
     */
    public Token getNextToken() throws IOException {
        Token token = null;
        while (charIsOnArray((char) now, whiteSpace)) {
            // killing the white space stuff.
            readNextChar();
        }
        boolean result;
        boolean gotToken = false;
        while (!gotToken) {
            if (automatas.isEmpty()) {
                if (now == -1) {
                    Token eof = new Token(TokenType.EOF, -1);
                    eof.setLine(line);
                    return eof;
                }
                initAutomatas();
            }
            ArrayList<Automaton> newAutomataList = new ArrayList<Automaton>();
            for (Automaton automaton : automatas) {
//                System.out.println( "Automata " + automatas.indexOf(automaton));
                result = automaton.process((char) now);
//                System.out.println(" retornou " + result + " com " + (char) now);
                if (!result) {
                    //checks if it is on final state.
                    if (automaton.isOnFinalState()) {
//                        System.out.println( "Automata " + automatas.indexOf(automaton) +"em estado final");
                        token = automaton.getToken();
                        gotToken = true;
                        newAutomataList.add(automaton);
                        break;
                    }
                } else {
                    newAutomataList.add(automaton);
                }
            }
            automatas = newAutomataList;
            if (!gotToken) {
                readNextChar();
            }
        }
        if (now == '\n') {
            token.setLine(line - 1);
        } else {
            token.setLine(line);
        }
        if (token.getType() == TokenType.IDENTIFIER) {
//            System.out.println(((WordAutomaton) automatas.get(0)).getName());
        }
        // all to start again
        initAutomatas();
        return token;

    }

    /**
     * Inicializa / reseta os automatos do analisador léxico
     */
    private void initAutomatas() {
        automatas = new ArrayList<Automaton>();
        automatas.add(new WordAutomaton());
        automatas.add(new NumberAutomaton());
        automatas.add(new StringAutomaton());
    }

    /**
     * Le o proximo caractere do arquivo.
     * @throws IOException
     */
    private void readNextChar() throws IOException {
        now = fileReader.read();
        if (now == '\n') {
            line++;
        }
    }
}
