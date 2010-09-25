 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lex;

import java.io.IOException;
import java.net.URL;
import org.junit.After;
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

    @After
    public void tearDown() throws IOException{
        analyzer.resetAnalyzer();
    }

    /**
     * Tests if the analyzer is a single ton object, it will be requested by
     * different components, it should always be the same object to work.
     */
    @Test
    public void testSingleTon() {
        Analyzer secondInstance = Analyzer.getInstance();
        secondInstance.resetAnalyzer();
        assertSame("The singleton pattern is not working...", analyzer, secondInstance);
    }
    
    @Test
    public void testEmptyLoadFile(){
        //An empty file is loaded
        URL sourceFile = AnalyzerTest.class.getResource("/lex/emptySource");
        analyzer.setFile(sourceFile.getFile());
        // the EOF token
        Token endOfFileToken = analyzer.getNextToken();
        assertEquals(TokenType.EOF , endOfFileToken.getType());
        
    }
    @Test
    public void testCommentAndNumber(){
        //The URL source file of this test
        analyzer = Analyzer.getInstance();

        URL sourceFile = AnalyzerTest.class.getResource("/lex/numberCommentSource");
        analyzer.setFile(sourceFile.getFile());
        Token Test = analyzer.getNextToken();
        System.out.println(Test);
        Test = analyzer.getNextToken();
        System.out.println(Test);
        Test = analyzer.getNextToken();
        System.out.println(Test);
    }


}