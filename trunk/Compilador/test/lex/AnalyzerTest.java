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
import utils.ArraysUtils;
import utils.LexicalException;
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
    public void tearDown() throws IOException {
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
    public void testEmptyLoadFile() throws LexicalException {
        //An empty file is loaded
        URL sourceFile = AnalyzerTest.class.getResource("/lex/emptySource");
        analyzer.setFile(sourceFile.getFile());
        // the EOF token
        Token endOfFileToken = analyzer.getNextToken(null);
        assertEquals(TokenType.EOF, endOfFileToken.getType());

    }

    @Test
    public void testCommentAndNumber() throws LexicalException {
        //The URL source file of this test
        analyzer = Analyzer.getInstance();

        URL sourceFile = AnalyzerTest.class.getResource("/lex/numberCommentSource");
        analyzer.setFile(sourceFile.getFile());
        Token Test = analyzer.getNextToken(null);
        
        Test = analyzer.getNextToken(null);
        
        Test = analyzer.getNextToken(null);
        
    }

    @Test
    public void complexTest() throws LexicalException {

        SymbolTable table = new SymbolTable();
        analyzer.resetAnalyzer();
        URL source = AnalyzerTest.class.getResource("/lex/Test");
        analyzer.setFile(source.getFile());

        Token token = getPrivateWordToken("int", 10);
        Token identifier0 = new Token(TokenType.IDENTIFIER, 0);
        identifier0.setLine(10);
        Token identifier1 = new Token(TokenType.IDENTIFIER, 1);
        identifier1.setLine(10);
        assertEquals(token, analyzer.getNextToken(table));
        token = identifier0;
        assertEquals(token, analyzer.getNextToken(table));
        token = getPrivateWordToken("(", 10);
        assertEquals(token, analyzer.getNextToken(table));
        token = getPrivateWordToken("int", 10);
        assertEquals(token, analyzer.getNextToken(table));
        token = identifier1;
        assertEquals(token, analyzer.getNextToken(table));
        token = getPrivateWordToken(")", 10);
        assertEquals(token, analyzer.getNextToken(table));
        token = getPrivateWordToken("{", 10);
        assertEquals(token, analyzer.getNextToken(table));
        token = getPrivateWordToken("if", 11);
        assertEquals(token, analyzer.getNextToken(table));
        token = getPrivateWordToken("(", 11);
        assertEquals(token, analyzer.getNextToken(table));
        identifier1.setLine(11);
        token = identifier1;
        assertEquals(token, analyzer.getNextToken(table));
        token = getPrivateWordToken("==", 11);
        assertEquals(token, analyzer.getNextToken(table));
        token = new Token(TokenType.INT, 0);
        token.setLine(11);
        assertEquals(token, analyzer.getNextToken(table));
        token = getPrivateWordToken(")", 11);
        assertEquals(token, analyzer.getNextToken(table));
        token = getPrivateWordToken("{", 11);
        assertEquals(token, analyzer.getNextToken(table));
        token = getPrivateWordToken("return", 12);
        assertEquals(token, analyzer.getNextToken(table));
        token = new Token(TokenType.INT, 1);
        token.setLine(12);
        assertEquals(token, analyzer.getNextToken(table));
        token = getPrivateWordToken(";", 12);
        assertEquals(token, analyzer.getNextToken(table));
        token = getPrivateWordToken("}", 13);
        assertEquals(token, analyzer.getNextToken(table));
        token = getPrivateWordToken("else", 13);
        assertEquals(token, analyzer.getNextToken(table));
        token = getPrivateWordToken("{", 13);
        assertEquals(token, analyzer.getNextToken(table));
        token = getPrivateWordToken("return", 14);
        assertEquals(token, analyzer.getNextToken(table));
        identifier0.setLine(14);
        token = identifier0;
        assertEquals(token, analyzer.getNextToken(table));
        token = getPrivateWordToken("(", 14);
        assertEquals(token, analyzer.getNextToken(table));
        identifier1.setLine(14);
        token = identifier1;
        assertEquals(token, analyzer.getNextToken(table));
        token = getPrivateWordToken("-", 14);
        assertEquals(token, analyzer.getNextToken(table));
        token = new Token(TokenType.INT, 1);
        token.setLine(14);
        assertEquals(token, analyzer.getNextToken(table));
        token = getPrivateWordToken(")", 14);
        assertEquals(token, analyzer.getNextToken(table));
        token = getPrivateWordToken(";", 14);
        assertEquals(token, analyzer.getNextToken(table));
        token = getPrivateWordToken("}", 15);
        assertEquals(token, analyzer.getNextToken(table));
        token = getPrivateWordToken("}", 16);
        assertEquals(token, analyzer.getNextToken(table));
        token = getPrivateWordToken("int", 19);
        assertEquals(token, analyzer.getNextToken(table));
        Token identifier2 = new Token(TokenType.IDENTIFIER, 2);
        identifier2.setLine(19);
        token = identifier2;
        assertEquals(token, analyzer.getNextToken(table));
        token = getPrivateWordToken("(", 19);
        assertEquals(token, analyzer.getNextToken(table));
        token = getPrivateWordToken(")", 19);
        assertEquals(token, analyzer.getNextToken(table));
        token = getPrivateWordToken("{", 19);
        assertEquals(token, analyzer.getNextToken(table));
        token = getPrivateWordToken("return", 20);
        assertEquals(token, analyzer.getNextToken(table));
        Token identifier3;
        identifier3 = new Token(TokenType.IDENTIFIER, 3);
        identifier3.setLine(20);
        token = identifier3;
        assertEquals(token, analyzer.getNextToken(table));
        token = getPrivateWordToken("/", 20);
        assertEquals(token, analyzer.getNextToken(table));
        token = new Token(TokenType.INT, 2);
        token.setLine(20);
        assertEquals(token, analyzer.getNextToken(table));
        token = getPrivateWordToken(";", 20);
        assertEquals(token, analyzer.getNextToken(table));
        token = getPrivateWordToken("}",21);
        assertEquals(token, analyzer.getNextToken(table));
        token = new Token(TokenType.EOF, 0);
        token.setLine(21);
        assertEquals(token, analyzer.getNextToken(table));
    }

    private Token getPrivateWordToken(String word, int line) {
        Token token = new Token(TokenType.RESERVED_WORD, ArraysUtils.getReservedWordIndex(word));
        token.setLine(line);
        return token;
    }
}
