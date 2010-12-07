/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lex.automaton;

import lex.Token;
import lex.TokenType;
import static utils.StringStore.stringList;

/**
 *
 * @author helionagamachi
 */
public class StringAutomaton extends Automaton{
    
    private boolean finalState;
    private char open ;
    private String store;
    private Token token;

    public StringAutomaton() {
        store = "";
        finalState = false;
        open = 0;
        token = null;
    }


    @Override
    public boolean process(char c) {
        if(finalState){
            // não aceita mais caracteres
            return false;
        }
        if(open == 0){
            // espero por aspas simples ou duplas
            if(c == '\'' || c=='"'){
                open = c;
                return true;
            }
        }else{
            if(c == open){
                //fechou a string
                finalState = true;
                prepareToken();
                return true;
            }else{
                // conteudo da string
               store += c;
               return true;
            }
        }
        return false;
    }

    @Override
    public boolean isOnFinalState() {
        return finalState;
    }

    @Override
    public void reset() {
        store = "";
        finalState = false;
        open = 0;
        token = null;
    }

    @Override
    public Token getToken() {
        return this.token;
    }

    private void prepareToken(){
        int index;
        stringList.add(store);
        index = stringList.indexOf(store);
        token = new Token(TokenType.CADEIA, index);
    }
}
