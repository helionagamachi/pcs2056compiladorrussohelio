/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automaton;

import lex.Token;
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
public class CharAutomataTest {


    CharAutomata automata;
    public CharAutomataTest() {
        automata = new CharAutomata();
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }


    @Test
    public void normalTesting(){
        testString("'n'", 'n');
        testString("'a'", 'a');
        testString("'b'", 'b');
        testString("'c'", 'c');
        testString("'o'", 'o');
        testString("'p'", 'p');
        testString("'0'", '0');
        testString("'1'", '1');
        testString("'2'", '2');
        testString("'W'", 'W');
    }

    @Test
    public void escapeTesting(){
        testString("'\\n'", '\n');
        testString("'\\\\'", '\\');
        testString("'\\r'", '\r');
        testString("'\\t'", '\t');
        testString("'\\\"'", '"');
    }

    public void testString(String charString, char expected){
        for(char c : charString.toCharArray()){
            automata.processChar(c);
        }
        Token token = automata.getToken();
        assertEquals(expected , token.getValue());
        automata.resetAutomata();
    }

}