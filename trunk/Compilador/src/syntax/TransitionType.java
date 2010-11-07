/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package syntax;

/**
 * To indicate if the transition will be a normal one
 * or it will result on a call to another automata
 */
public enum TransitionType {
    //normal transtion, the finite automata, changed it's state.
    NORMAL,
    //Transition that requires the call of another automata.
    CALL_TO_ANOTHER_AUTOMATA,
    //The automata has nothing do anymore, should return.
    GO_BACK;
}
