/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automaton;

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
public class WordTest {

    static WordAutomata wordAutomata ;
    public WordTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        wordAutomata = new WordAutomata();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        assertEquals("Should be on the initial state",State.INITIAL ,wordAutomata.getState());
    }

    @After
    public void tearDown() {
        wordAutomata.resetAutomata();
    }

    @Test
    public void testNumber(){
        assertFalse("Should not accept a number as first char" , wordAutomata.processChar('9'));
    }

    @Test
    public void testComment(){
        assertFalse("Should not accept a open of comment as first char", wordAutomata.processChar('/'));
    }

    @Test
    public void testReserved(){
        // Try the reserved word while
        assertTrue(wordAutomata.processChar('w'));
        assertEquals("Should be on possible reserved word State!",wordAutomata.currentState , State.POSSIBLE_RESERVED_WORD);
        assertTrue(wordAutomata.processChar('h'));
        assertTrue(wordAutomata.processChar('i'));
        assertTrue(wordAutomata.processChar('l'));
        assertTrue(wordAutomata.processChar('e'));
        assertFalse(wordAutomata.processChar('('));
        assertEquals(wordAutomata.getState(), State.RESERVED_WORD);
        assertEquals("while", wordAutomata.getName());
    }

    @Test
    public void testOperator(){
        assertTrue(wordAutomata.processChar('('));
        assertFalse(wordAutomata.processChar('('));
        assertEquals(State.RESERVED_WORD, wordAutomata.currentState);
    }
    
    @Test
    public void testOtherOperator(){
        assertTrue(wordAutomata.processChar('+'));
        assertTrue(wordAutomata.processChar('+'));
        assertFalse(wordAutomata.processChar(' '));
        assertEquals("++", wordAutomata.getName());
    }

    @Test
    public void testOtherOperator2(){
        assertTrue(wordAutomata.processChar('+'));
        assertTrue(wordAutomata.processChar('='));
        assertFalse(wordAutomata.processChar(' '));
        assertEquals("+=", wordAutomata.getName());
    }
}