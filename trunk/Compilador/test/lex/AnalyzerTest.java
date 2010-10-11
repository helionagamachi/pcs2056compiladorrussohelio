 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lex;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.Others;
import utils.SymbolTable;
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
        Token endOfFileToken = analyzer.getNextToken(null);
        assertEquals(TokenType.EOF , endOfFileToken.getType());
        
    }
    @Test
    public void testCommentAndNumber(){
        //The URL source file of this test
        analyzer = Analyzer.getInstance();

        URL sourceFile = AnalyzerTest.class.getResource("/lex/numberCommentSource");
        analyzer.setFile(sourceFile.getFile());
        Token Test = analyzer.getNextToken(null);
        System.out.println(Test);
        Test = analyzer.getNextToken(null);
        System.out.println(Test);
        Test = analyzer.getNextToken(null);
        System.out.println(Test);
    }

    @Test
    public void complexTest(){

        SymbolTable table = new SymbolTable();
        analyzer.resetAnalyzer();
        URL source = AnalyzerTest.class.getResource("/lex/Test");
        analyzer.setFile(source.getFile());
        
        System.out.println("-------------------------------");
        System.out.println();

        ArrayList<Token> tokens = new ArrayList<Token>();
        Token token = analyzer.getNextToken(table);
        while(token.getType() != TokenType.EOF){
            tokens.add(token);
            token = analyzer.getNextToken(table);
        }
        tokens.add(token);
        for(Token temp : tokens){
            Others.printToken(temp , table);
        }
        System.out.println();
        System.out.println("-------------------------------");
        
    }

    

}