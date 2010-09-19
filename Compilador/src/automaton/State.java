/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automaton;

import java.util.HashMap;
import org.apache.log4j.Logger;
import lex.Token;

/**
 *
 * @author Helio
 */
public class State {

    private static Logger LOGGER = Logger.getLogger(State.class);

    private HashMap<Character,State> transitions;
    private boolean finalState;
    private Token token;
    // for debugging
    private String name;

    public State (boolean isFinal , Token token , String name){
        this.finalState = isFinal;
        this.token = token;
        this.name = name;
        this.transitions = new HashMap<Character, State>();
    }

    public State nextState(Character c){
        LOGGER.debug("On state " + this.name);
        if(this.transitions.containsKey(c)){
            return this.transitions.get(c);
        }else{
            return this;
        }
    }

    public void setTransition(Character c, State nextState){
        LOGGER.debug("State " + this.name + " recieves transition, char "  + c + " to " + nextState.getName());
        this.transitions.put(c, nextState);
    }

    public boolean isFinal(){
        return this.finalState;
    }

    public Token getToken(){
        return this.token;
    }

    protected String getName(){
        return this.name;
    }
}
