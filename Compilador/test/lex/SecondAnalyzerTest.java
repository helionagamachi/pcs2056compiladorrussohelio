/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lex;

import utils.Others;
import java.util.ArrayList;
import java.net.URL;
import utils.SymbolTable;
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
public class SecondAnalyzerTest {


       private static Analyzer analyzer;
    public SecondAnalyzerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {

        analyzer = Analyzer.getInstance();
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

    /**
     * Need to test the division symbol against comments...
     */
    @Test
    public void evilTest(){
        SymbolTable table = new SymbolTable();
        analyzer.resetAnalyzer();
        URL source = AnalyzerTest.class.getResource("/lex/NumberCommentSource");
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

}