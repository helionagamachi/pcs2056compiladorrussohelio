/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automaton;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Helio
 */
public class NumberTest {

    public NumberTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testNumberAutomata(){
        NumberAutomata num = new NumberAutomata();
        assertFalse("Should not accept anything besides numbers and '.'", num.processChar(' '));
        assertFalse("Should not accept anything besides numbers and '.'", num.processChar('a'));
        assertFalse("Should not accept anything besides numbers and '.'", num.processChar('v'));
        assertFalse("Should not accept anything besides numbers and '.'", num.processChar('\n'));
        assertEquals("Should be on the initial State",State.INITIAL, num.getState());
        //Tests a integer input;
        assertTrue("Should accept an Integer input", num.processChar('0'));
        assertTrue("Should accept an Integer input", num.processChar('5'));
        assertTrue("Should accept an Integer input", num.processChar('6'));
        assertTrue("Should accept an Integer input", num.processChar('9'));
        assertTrue("Should accept an Integer input", num.processChar('0'));
        assertTrue("Should accept an Integer input", num.processChar('1'));
        assertTrue("Should accept an Integer input", num.processChar('1'));
        assertTrue("Should accept an Integer input", num.processChar('1'));
        assertEquals("Should be on the Integer State",State.INTEGER, num.getState());
        assertEquals("Should have 5690111", 5690111, num.getIntegerNumber());
        //Puts a dot , and should turn to be a float number.
        assertTrue("Should accept a dot now", num.processChar('.'));
        assertTrue("Should accept an integer input", num.processChar('1'));
        assertTrue("Should accept an integer input", num.processChar('2'));
        assertTrue("Should accept an integer input", num.processChar('3'));
        assertEquals("Should be on the float State", State.FLOAT , num.getState());
        assertEquals("Should have 5690111.123", (float)5690111.123 , num.getFloatNumber() , 0.001);
        assertFalse("Should not accept a dot now", num.processChar('.'));
        //Testing for numbers like .0001, first reset the automata
        num.resetAutomata();
        assertEquals("Should be on the initial State",State.INITIAL, num.getState());
        assertTrue("Should accept a dot now", num.processChar('.'));
        assertTrue("Should accept an integer input", num.processChar('1'));
        assertTrue("Should accept an integer input", num.processChar('2'));
        assertTrue("Should accept an integer input", num.processChar('3'));
        assertEquals("Should be on the float State", State.FLOAT , num.getState());
        assertEquals("Should have 5690111.123", (float)0.123 , num.getFloatNumber() , 0.001);   
    }

}