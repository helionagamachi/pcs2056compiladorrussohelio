/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automaton;

import java.net.URL;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests on comments, for while tests the comment Automata,
 * should test the lex Analyzer with files that only contains comments.
 * @author Helio
 */
public class CommentTest {

    public CommentTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
       //The comment file must be present
//       URL commentFile = CommentTest.class.getResource("/automaton/testComment.txt");
//       assertNotNull("The comment file must be in place.",commentFile);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    //Tests a somehow the comment Automata.
    @Test
    public void testComentAutomata(){
        CommentAutomata cm = new CommentAutomata();
        assertEquals("Should be on the initial State", State.INITIAL , cm.getState());
        assertNull("Should never return a token", cm.getToken());
        assertFalse("Something different from / is not a comment Start", cm.processChar('a'));
        assertFalse("Something different from / is not a comment Start", cm.processChar(' '));
        assertFalse("Something different from / is not a comment Start", cm.processChar('\''));
        assertFalse("Something different from / is not a comment Start", cm.processChar('*'));
        assertFalse("Something different from / is not a comment Start", cm.processChar('K'));
        assertFalse("Something different from / is not a comment Start", cm.processChar('D'));
        assertFalse("Something different from / is not a comment Start", cm.processChar('\t'));
        assertFalse("Something different from / is not a comment Start", cm.processChar('\n'));
        assertTrue("Should accept the initial / ", cm.processChar('/'));
        assertTrue("Should accept other /, line comment", cm.processChar('/'));
        assertEquals("Should be on the line comment state", State.COMMENT_LINE , cm.getState());
        assertNull("Should never return a token", cm.getToken());
        assertTrue("Should accept any char on the line, except the new line", cm.processChar('a'));
        assertTrue("Should accept any char on the line, except the new line", cm.processChar('B'));
        assertTrue("Should accept any char on the line, except the new line", cm.processChar(')'));
        assertTrue("Should accept any char on the line, except the new line", cm.processChar('*'));
        assertTrue("Should accept any char on the line, except the new line", cm.processChar('/'));
        assertTrue("Should accept any char on the line, except the new line", cm.processChar('\\'));
        assertTrue("Should accept any char on the line, except the new line", cm.processChar('\''));
        assertTrue("Should accept any char on the line, except the new line", cm.processChar('"'));
        assertTrue("Should accept any char on the line, except the new line", cm.processChar('\r'));
        assertTrue("Should accept any char on the line, except the new line", cm.processChar('d'));
        assertTrue("Should accept any char on the line, except the new line", cm.processChar('w'));
        assertTrue("Should accept any char on the line, except the new line", cm.processChar('q'));
        assertFalse("The comment should end with a unix new line char", cm.processChar('\n'));
        assertEquals("Should be on the initial State", State.INITIAL , cm.getState());
        assertFalse("Should be at the start, giving something different of /", cm.processChar('a'));
        assertTrue("Should accept the initial / ", cm.processChar('/'));
        assertTrue("Should accept the *", cm.processChar('*'));
        assertEquals("Should be on the comment block state", State.COMMENT_BLOCK, cm.getState());
        assertNull("Should never return a token", cm.getToken());
        assertTrue("Should accept any chars , but the sequence */",cm.processChar('a'));
        assertTrue("Should accept any chars , but the sequence */",cm.processChar('b'));
        assertTrue("Should accept any chars , but the sequence */",cm.processChar('d'));
        assertTrue("Should accept any chars , but the sequence */",cm.processChar('D'));
        assertTrue("Should accept any chars , but the sequence */",cm.processChar('H'));
        assertTrue("Should accept any chars , but the sequence */",cm.processChar('_'));
        assertTrue("Should accept any chars , but the sequence */",cm.processChar(' '));
        assertTrue("Should accept any chars , but the sequence */",cm.processChar('\\'));
        assertTrue("Should accept any chars , but the sequence */",cm.processChar('\n'));
        assertTrue("Should accept any chars , but the sequence */",cm.processChar('\r'));
        assertTrue("Should accept any chars , but the sequence */",cm.processChar('*'));
        assertTrue("Should accept any chars , but the sequence */",cm.processChar('\n'));
        //ending the comment Block
        assertTrue("Should accept any chars , but the sequence */",cm.processChar('*'));
        assertNull("Should never return a token", cm.getToken());
        assertFalse("Should accept any chars , but the sequence */",cm.processChar('/'));
        assertEquals("Should be at the initial state" , State.INITIAL , cm.getState());
        assertNull("Should never return a token", cm.getToken());


    }

}