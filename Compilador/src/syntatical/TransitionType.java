/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package syntatical;

/**
 * To indicate if the transition will be a normal one
 * or it will result on a call to another automata
 */
public enum TransitionType {

    NORMAL, CALL_TO_ANOTHER_AUTOMATA;
}
