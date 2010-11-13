/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package syntax;

import java.io.File;
import lex.Token;
import lex.TokenType;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.ArrayUtilsTest;
import utils.ArraysUtils;
import static org.junit.Assert.*;

/**
 *
 * @author helionagamachi
 */
public class StructedAutomataTest {

    public StructedAutomataTest() {
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
    public void test(){
        String[] filesPath = new String[2];
        filesPath[0] = StructedAutomataTest.class.getResource("/syntax/config/test/lol").getFile();
        filesPath[1] = StructedAutomataTest.class.getResource("/syntax/config/test/lol2").getFile();

        StructedAutomata automata;
        automata = new StructedAutomata(2);
        automata.init(filesPath);

        Token idToken = new Token(TokenType.IDENTIFIER, -1);
        Token plus = new Token(TokenType.RESERVED_WORD, ArraysUtils.getReservedWordIndex("+"));

        assertTrue(automata.nextStep(plus));
        assertTrue(automata.nextStep(idToken));
        assertTrue(automata.accepted());
        
    }

    
}