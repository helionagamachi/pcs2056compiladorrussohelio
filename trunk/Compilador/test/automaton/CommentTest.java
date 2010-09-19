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
 *
 * @author Helio
 */
public class CommentTest {

    public CommentTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
       //The comment file must be present
       URL commentFile = CommentTest.class.getResource("/automaton/testComment.txt");
       assertNotNull("The comment file must be in place.",commentFile);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void test(){
    }

}