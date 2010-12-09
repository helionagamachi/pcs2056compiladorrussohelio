/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package semantic;

import codeGeneration.Coder;
import java.util.ArrayList;
import lex.Token;
import lex.TokenType;
import utils.CompilerException;
import static lex.ReservedWords.getByIndex;
import utils.SymbolTable;

/**
 * This class will gives the support to all code that is related with
 * the semantics of the compiler
 * @author helionagamachi
 */
public class Semantic {

    protected static ArrayList<Token> tokenList = new ArrayList<Token>();
    private Token latestToken;
    public static SymbolTable rootTable = new SymbolTable();
    public static SymbolTable latestTable;
    private static Coder coder;
    private static String operator = "";
    

    public static void initCoder() {
        coder = Coder.getInstance();
    }

    public Semantic() {
        System.out.println("Semantic Constructor called");
        if (latestTable == null) {
            latestTable = rootTable;
        }
    }

    /**
     * Adds a token to the list, this list will be used by the actions to do stuff.
     * @param token
     */
    public void addToken(Token token) {
        tokenList.add(token);
        latestToken = token;
    }

    public void runAction(String name) throws CompilerException {
        if (name.equals("num")) {
            int valor = latestToken.getValue();
            coder.putOnBuffer("iconst_" + valor + "\n", false);
            coder.putOnBuffer(operator + "\n", false);
            operator = "";
        } else if(name.equals("new_op")){
            int value = latestToken.getValue();
            String word = getByIndex(value);
            if(word.equals("+")){
                operator = "iadd";
            }else if(word.equals("-")){
                operator = "isub";
            }else if(word.equals("/")){
                operator = "idiv";
            }else if(word.equals("*")){
                operator = "imul";
            }
        }
        else {

        }

    }

    public void runFinalAction(int machineNumber) {
        //When the machine will return to another one, it can execute a final action;
        System.out.println("Running the final action to the machine " + machineNumber);

        switch (machineNumber) {
            case 0:
                //PROGRAM;
                
            case 1:
                //Code block
                break;
            case 2:

                // <editor-fold defaultstate="collapsed" desc=" Expression ">

                break;

        }
    }


}
