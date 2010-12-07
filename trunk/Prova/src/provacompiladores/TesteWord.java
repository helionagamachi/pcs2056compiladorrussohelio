/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package provacompiladores;

import java.util.ArrayList;
import lex.automaton.WordAutomaton;

/**
 *
 * @author helionagamachi
 */
public class TesteWord {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        WordAutomaton wor = new WordAutomaton();
        System.out.println(wor.process('('));
        wor.reset();
        System.out.println(wor.process('n'));
        System.out.println(wor.process(')'));

        ArrayList<String> a = new ArrayList<String>();

        a.add("lol");
        System.out.println(a.indexOf("lol"));

        
    }

}
