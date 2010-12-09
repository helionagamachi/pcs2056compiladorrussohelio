/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package syntax;

import lex.Token;
import lex.TokenType;
import semantic.Semantic;
import syntax.config.Parser;
import utils.LexicalException;
import utils.SymbolTable;

/**
 * This class takes cares on analyzing
 * a code file syntax.
 * 
 * @author helionagamachi
 */
public class Analyzer {

    private lex.Analyzer lexical;
    private StructedAutomata structedAutomata;
    public static final String[] files = {"programa" , "bloco_codigo" ,  "expressao"};

    public Analyzer() {
        //makes the parser listing
        Parser.fillNamesList(files);
        lexical = lex.Analyzer.getInstance();
        structedAutomata = new  StructedAutomata(4);
        structedAutomata.init(getFilePaths());
        
    }


    public void reset(){
        structedAutomata.setAutomataAndState(0, 0);
        AutomataStack stack = AutomataStack.getInstance();
        while(!stack.isEmpty()){
            stack.pop();
        }
    }
    public void setFile(String filePath){
        lexical.setFile(filePath);
    }

    /**
     * returns true if the program is valid.
     * @return
     */
    public boolean analyze() throws LexicalException{
        Token token ;
        SymbolTable table = Semantic.latestTable;
        token = lexical.getNextToken(table);
        while(token.getType() != TokenType.EOF){
            boolean result;
            System.out.println("Syntax analyzer got this token " + token);
            result = structedAutomata.nextStep(token);
            if(!result){
                if(structedAutomata.accepted()){
                    return true;
                }
                return false;
            }
//            System.out.println("getting next token");
            token = lexical.getNextToken(table);
        }
        return true;
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
