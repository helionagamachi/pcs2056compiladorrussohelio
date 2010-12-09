/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package syntax;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import lex.Token;
import lex.TokenType;
//import semantic.Semantic;
//import syntax.config.Parser;

//import utils.SymbolTable;

/**
 * This class takes cares on analyzing
 * a code file syntax.
 * 
 * @author helionagamachi
 */
public class Analyzer {

    private lex.Analyzer lexical;
    private StructedAutomata structedAutomata;
    public static final String[] files = { "comando" ,  "exp"  , "funcao"};

    public Analyzer(File file) throws FileNotFoundException, IOException {
        //makes the parser listing
        
        lexical = new lex.Analyzer(file);
        structedAutomata = new  StructedAutomata(3);
        structedAutomata.init(getFilePaths());
        
    }


    public void reset(){
        structedAutomata.setAutomataAndState(0, 0);
        AutomataStack stack = AutomataStack.getInstance();
        while(!stack.isEmpty()){
            stack.pop();
        }
    }

    /**
     * returns true if the program is valid.
     * @return
     */
    public boolean analyze() throws IOException{
        Token token ;
//        SymbolTable table = Semantic.latestTable;
        token = lexical.getNextToken();
        while(token.getType() != TokenType.EOF){
            boolean result;
//            System.out.println("Syntax analyzer got this token " + token);
            result = structedAutomata.nextStep(token);
            if(!result){
                if(structedAutomata.accepted()){
                    System.out.println("Valid Program");
                    return true;
                }
                System.out.println("Invalid Program");
                return false;
            }
//            System.out.println("getting next token");
            token = lexical.getNextToken();
        }
        System.out.println("Invalid Program");
        return false;
    }


    /**
     * Gets the paths to the files, using the files vector, the files vector should have the names of the files
     * @return array containing the paths
     */
    private String[] getFilePaths(){
        String[] result = new String[files.length];
        int index = 0;
        while(index < files.length){
            result[index] = Analyzer.class.getResource("/syntax/config/" + files[index]).getFile();
            index ++;
        }
        return result;
    }

    //TODO : CODE GENERATION....
}
