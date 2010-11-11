/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lex;

import automaton.Automata;
import automaton.CommentAutomata;
import automaton.NumberAutomata;
import automaton.WordAutomata;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author helionagamachi
 */
public class AnalyzerInternalTesting {

    private static Analyzer analyzer ;
    
    public AnalyzerInternalTesting() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        analyzer = Analyzer.getInstance();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        analyzer.resetAnalyzer();
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
    }

    @Test
    public void possibleAutomatas(){
        assertEquals("Should have 0 possible automatas", analyzer.possibleAutomatas.length  ,0);
        WordAutomata word = new WordAutomata();
        analyzer.addAutomata2List(word);
        assertEquals("Should have 1 possible automatas", analyzer.possibleAutomatas.length  ,1);
        CommentAutomata comment = new CommentAutomata();
        analyzer.addAutomata2List(comment);
        assertEquals("Should have 2 possible automatas", analyzer.possibleAutomatas.length  ,2);
        Automata array[] = {word, comment} ;
        assertEquals("Should have this array of possible automatas", analyzer.possibleAutomatas , array);
        NumberAutomata number = new NumberAutomata();
        analyzer.addAutomata2List(number);
        boolean toRemove[] = {true, false, false};
        analyzer.removeAutomatas(toRemove);
        assertEquals("Should have 2 possible automatas", analyzer.possibleAutomatas.length  ,2);
        array = new Automata[]{comment,number};
        assertEquals("Should have this array of possible automatas", analyzer.possibleAutomatas , array);

    }



}