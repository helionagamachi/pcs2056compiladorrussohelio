/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lex;

import java.net.URL;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Helio
 */
public class AnalyzerTest {

    public AnalyzerTest() {
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
    public void testSomeMethod() {
        URL testFile = this.getClass().getResource("/test");
        Analyzer a = new Analyzer(testFile.getFile());
    }

}