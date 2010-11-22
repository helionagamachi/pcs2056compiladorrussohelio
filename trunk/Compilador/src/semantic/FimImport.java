/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package semantic;

import lex.Token;

/**
 * The import Done will just clear the tokens list, for now its
 * not possible to import other codes.
 * @author helionagamachi
 */
public class FimImport extends Action{

    /**
     * Right now just clears the list of tokens
     * @param token
     */
    @Override
    public void run(Token token) {
        Semantic.tokenList.clear();
        
    }

}
