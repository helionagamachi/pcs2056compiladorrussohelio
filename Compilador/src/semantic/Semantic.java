/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package semantic;

import utils.SymbolType;
import utils.SymbolLine;
import lex.Token;
import lex.TokenType;
import org.apache.log4j.Logger;
import utils.CompilerException;
import utils.SymbolTable;
import static utils.ArraysUtils.getReservedWordByIndex;
import static utils.ArraysUtils.getReservedWordIndex;


/**
 * This class will gives the support to all code that is related with
 * the semantics of the compiler
 * @author helionagamachi
 */
public class Semantic {

    protected static TokensList tokenList = new TokensList();
    private Token latestToken;
    public static SymbolTable rootTable = new SymbolTable();
    public static SymbolTable latestTable;
    private static final Logger LOGGER = Logger.getLogger(Semantic.class);

    public Semantic() {
        if (latestTable == null) {
            latestTable = rootTable;
        }
    }

    /**
     * Adds a token to the list, this list will be used by the actions to do stuff.
     * @param token
     */
    public  void addToken(Token token) {
        tokenList.addToken(token);
        latestToken = token;
    }

    public void runAction(String name) throws CompilerException {
        LOGGER.debug("Asked to run the action " + name);
        if(name.equals("fim_import")){
            fimImport();
        }else if(name.equals("new_context")){
            newContext();
        }else if(name.equals("new_func")){
            newFunc();
        }else if(name.equals("new_var")){
            newVar();
        }else if(name.endsWith("fim_vars")){
            fimVar();
        }
        else{
            LOGGER.warn("Action " + name + " not defined !");
        }

    }


    /**
     * Imports only causes the token list to be cleared
     */
    private void fimImport(){
        tokenList.clear();
    }


    private void newContext(){
        tokenList.clear();
        //TODO : SYMBOL TABLE.
    }

    private void newFunc(){
        tokenList.clear();
    }

    
    

    
    // <editor-fold  desc="Vars actions...">
    /**
     * Takes care when a new var is declared.
     * @throws CompilerException
     */
    private void newVar() throws CompilerException{
        Token[] array;
        array = Semantic.tokenList.getArray();
        Token RW_INT = new Token(TokenType.RESERVED_WORD, getReservedWordIndex("int"));
        Token RW_STRING = new Token(TokenType.RESERVED_WORD, getReservedWordIndex("String"));
        Token RW_CHAR = new Token(TokenType.RESERVED_WORD, getReservedWordIndex("char"));
        Token RW_BOOL = new Token(TokenType.RESERVED_WORD, getReservedWordIndex("bool"));
        Token type = array[0];
        if (type.getType() == TokenType.RESERVED_WORD) {
            // checks if it is one of the above, if not, error should happen...
            boolean intComp, stringComp, charComp, boolComp, ok;
            intComp = RW_INT.compatible(type);
            stringComp = RW_STRING.compatible(type);
            charComp = RW_CHAR.compatible(type);
            boolComp = RW_BOOL.compatible(type);
            ok = intComp || stringComp || charComp || boolComp;
            if (!ok) {
                String errorMessage = "Unexpected reserved word , " + getReservedWordByIndex(type.getValue());
                errorMessage = errorMessage + " on line " + type.getLine();
                throw new CompilerException(errorMessage);
            }
        } else if (type.getType() == TokenType.IDENTIFIER) {
            // User type var...
            String errorMessage = "User types not supported yet." ;
            errorMessage = errorMessage + " on line " + type.getLine();
            errorMessage = errorMessage + " the type was " + latestTable.getLine(type.getValue()).getSymbol();
            throw new CompilerException(errorMessage);
        }
        int index = latestToken.getValue();
        SymbolLine line ;
        line = Semantic.latestTable.getLine(index);
        line.setType(SymbolType.VAR);
    }

    /**
     * When the new Var is done...
     */
    private void fimVar(){
        tokenList.clear();
    }
    // </editor-fold>
}
