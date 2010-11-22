/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package semantic;

import lex.TokenType;
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
public class tokenStackTest {

    public tokenStackTest() {
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
    public void test() {
        Token token1 = new Token(TokenType.INT, 2);
        Token token2 = new Token(TokenType.RESERVED_WORD, 2);
        Token token4 = new Token(TokenType.IDENTIFIER, 12);
        Token token3 = new Token(TokenType.CHAR, 's');

        TokenStack stack = new TokenStack();
        assertTrue(stack.isEmpty());
        stack.push(token1);
        stack.push(token1);
        stack.push(token2);
        stack.push(token3);
        stack.push(token4);
        stack.push(token2);
        stack.push(token3);

        assertEquals(token3, stack.pop());
        assertEquals(token2, stack.pop());
        assertEquals(token4, stack.pop());
        assertEquals(token3, stack.pop());
        assertEquals(token2, stack.pop());
        assertEquals(token1, stack.pop());
        assertEquals(token1, stack.pop());
        assertEquals(null, stack.pop());
        assertTrue(stack.isEmpty());

    }
}
