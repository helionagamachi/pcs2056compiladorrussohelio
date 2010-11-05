/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package syntatical;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.CompilerException;
import static org.junit.Assert.*;

/**
 *
 * @author helionagamachi
 */
public class FiniteAutomataTest {

    public FiniteAutomataTest() {
    }
    FiniteAutomata auto1;

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
        int finalStates[] = {4};
        auto.initStates(finalStates);
        try {
            auto.transit(null);
            fail("Should have got an exception");
        } catch (CompilerException e) {
        }
    }
}
