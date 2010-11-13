/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package syntax;

import lex.Token;
import lex.TokenType;
import syntax.FiniteAutomata;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.ArraysUtils;
import utils.CompilerException;
import static org.junit.Assert.*;

/**
 *
 * @author helionagamachi
 */
public class FiniteAutomataTest {

    public FiniteAutomataTest() {
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
    public void ExceptionTesting() {
        FiniteAutomata auto = new FiniteAutomata(5, 0, 0);
        int finalStates[] = {1, 2};
        auto.initStates(finalStates);
        
        try {
            auto.transit(null);
            fail("Should have got an exception");
        } catch (CompilerException e) {
        }
    }


    @Test
    public void TransitionTesting() throws CompilerException{
        FiniteAutomata auto = new FiniteAutomata(3, 2, 0);
        int[] finalStates = {2};
        Token continueToken = new Token(TokenType.RESERVED_WORD, ArraysUtils.getReservedWordIndex("contiune"));
        Transition transition1 = new Transition(0, 0, 1, continueToken);
        Transition transition2 = new Transition(0, 1, 2, 1);
        Transition[] transitions = {transition1 , transition2};
        auto.initStates(finalStates);
        auto.setTransitions(transitions);
        auto.transit(continueToken);
        auto.transit(continueToken);
    }
}
