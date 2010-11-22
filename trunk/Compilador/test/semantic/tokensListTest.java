/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package semantic;

import lex.Token;
import lex.TokenType;
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
public class tokensListTest {

    public tokensListTest() {
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
    public void Test1(){
        Token token1 = new Token(TokenType.INT, 2);
        Token token2 = new Token(TokenType.RESERVED_WORD, 2);
        Token token4 = new Token(TokenType.IDENTIFIER, 12);
        Token token3 = new Token(TokenType.CHAR, 's');

        TokensList list = new TokensList();
        assertEquals(0, list.getCount());
        list.addToken(token1);
        list.addToken(token1);
        list.addToken(token2);
        list.addToken(token3);
        list.addToken(token4);

        Token[] array = list.getArray();
        assertEquals(5  , array.length);
        assertEquals(token1, array[0]);
        assertEquals(token1, array[1]);
        assertEquals(token2, array[2]);
        assertEquals(token3, array[3]);
        assertEquals(token4, array[4]);
        
        list.clear();
        
        assertEquals(0, list.getCount());
        assertEquals(0, list.getArray().length);


    }

}