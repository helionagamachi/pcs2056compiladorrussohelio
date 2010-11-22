/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package semantic;

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
public class actionListTest {

    public actionListTest() {
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
    public void Test() {
        ActionList list = new ActionList();
        mockUpAction mock = new mockUpAction();
        FimImport importDone = new FimImport();
        list.addAction("mock", mock);
        list.addAction("import", importDone);
        assertEquals(mock, list.getAction("mock"));
        assertEquals(importDone, list.getAction("import"));
    }

    private class mockUpAction extends Action {

        @Override
        public void run(Token token) {
            return;
        }
    }
}
