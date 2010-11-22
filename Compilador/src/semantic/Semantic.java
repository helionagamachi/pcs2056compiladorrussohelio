/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package semantic;

import lex.Token;

/**
 * This class will gives the support to all code that is related with
 * the semantics of the compiler
 * @author helionagamachi
 */
public class Semantic {
    protected static TokensList tokenList = new TokensList();
    private static ActionList list = new ActionList();


    public static void addToken(Token token){
        tokenList.addToken(token);
    }

    /**
     * Inits the list of actions .
     */
    public static void init(){
        list.addAction("fim_import", new FimImport());
    }

    /**
     * Gets an action based on the name.
     * @param name
     * @return
     */
    public static Action getAction(String name){
        return list.getAction(name);
    }
    
}
