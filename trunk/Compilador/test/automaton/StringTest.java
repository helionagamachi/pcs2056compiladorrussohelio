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
 * @author Helio
 */
public class StringTest {

    static StringAutomata stringAutomata;

    public StringTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        stringAutomata = new StringAutomata();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp(){
        assertEquals("Should be on the initial State", State.INITIAL, stringAutomata.currentState);
    }

    @After
    public void tearDown(){
        stringAutomata.resetAutomata();
    }



    @Test
    public void testCommentStart(){
        assertFalse("Should not accept / on the initial state", stringAutomata.processChar('/'));
    }

    @Test
    public void testEmptyString(){
        assertTrue("Should accept Empty String", stringAutomata.processChar('"'));
        assertFalse("Should accept Empty String", stringAutomata.processChar('"'));
        assertEquals("Should be on the final State", State.STRING_FINAL, stringAutomata.currentState);
        assertEquals("Should have an empty String","", stringAutomata.getContent());
    }

}