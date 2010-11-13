/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

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
public class SymbolTableTest {


    static SymbolTable table;
    public SymbolTableTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        table = new SymbolTable();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void repetitionTest(){
        table.addLine("1");
        table.addLine("1");
        table.addLine("2");
        table.addLine("3");
        table.addLine("2");
        table.addLine("3");
        table.addLine("3");
        table.addLine("3");
        table.addLine("4");
        SymbolLine line = table.getLine(0);
        assertNotNull(line);
        assertEquals("1", line.getSymbol());
        line = line.getNextLine();
        assertEquals("2", line.getSymbol());
        line = line.getNextLine();
        assertEquals("3", line.getSymbol());
        line = line.getNextLine();
        assertEquals("4", line.getSymbol());
        line = line.getNextLine();
        assertNull(line);
    }

    @Test
    public void retrievalTest(){
        table.addLine("1");
        table.addLine("2");
        table.addLine("3");
        assertNull(table.getLine(4));
        assertEquals(table.getLine(2).getSymbol(), "3");
    }
    

    @Test
    public void basicNonRepetitionIndexTest(){
        int index ;
        index = table.addLine("fatorial");
        index = table.addLine("fatorial");
        index = table.addLine("fatorial");
        index = table.addLine("fatorial");
        index = table.addLine("fatorial");
        index = table.addLine("fatorial");
        index = table.addLine("fatorial");
        index = table.addLine("fatorial");
        assertEquals(0, index);
        index = table.addLine("inteiro");
        assertEquals(1, index);
        index = table.addLine("inteiro");
        index = table.addLine("inteiro");
        index = table.addLine("inteiro");
        assertEquals(1, index);
        index = table.addLine("numero");
        assertEquals(2, index);
        index = table.addLine("numero");
        assertEquals(2, index);
        index = table.addLine("fatorial");
        assertEquals(0, index);
    }



}