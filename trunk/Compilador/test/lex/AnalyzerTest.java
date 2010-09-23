/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lex;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Helio
 */
public class AnalyzerTest {

    private static Analyzer analyzer;


    public AnalyzerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        analyzer = Analyzer.getInstance();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Tests if the analyzer is a single ton object, it will be requested by
     * diferents componnents, it should always be the same object to work.
     */
    @Test
    public void testSingleTon() {
        Analyzer secondInstance = Analyzer.getInstance();
        assertSame("The singleton pattern is not working...", analyzer, secondInstance);
    }
    @Test
    public void testLoadFile() throws FileNotFoundException, IOException{
        //An empty file is loaded
        URL sourceFile = AnalyzerTest.class.getResource("/lex/emptySource");
        analyzer.setFile(sourceFile.getFile());
        // No token at all to me...
        Token nullToken = analyzer.getNextToken();
        assertNull(nullToken);
    }




}