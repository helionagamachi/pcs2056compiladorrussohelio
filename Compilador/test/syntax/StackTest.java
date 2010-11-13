/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package syntax;

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
public class StackTest {

    static AutomataStack stack;

    public StackTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        stack = AutomataStack.getInstance();
    }

    @After
    public void tearDown() {
    }


    @Test
    public void singleTon(){
        assertSame(stack, AutomataStack.getInstance());
    }



    @Test
    public void simpleTesting(){
        stack.push(0, 1);
        stack.push(1, 2);
        stack.push(3, 4);
        stack.push(5, 6);
        stack.push(0, 1);
        int[] result;
        result = stack.pop();
        assertEquals(0, result[0]);
        assertEquals(1, result[1]);
        result = stack.pop();
        assertEquals(5, result[0]);
        assertEquals(6, result[1]);
        result = stack.pop();
        assertEquals(3, result[0]);
        assertEquals(4, result[1]);
        result = stack.pop();
        assertEquals(1, result[0]);
        assertEquals(2, result[1]);
        result = stack.pop();
        assertEquals(0, result[0]);
        assertEquals(1, result[1]);
        assertTrue(stack.isEmpty());
        stack.push(1, 1);
        assertFalse(stack.isEmpty());
        result = stack.pop();
        assertEquals(1, result[0]);
        assertEquals(1, result[1]);
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.pop().length);
    }

}