package utils;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import org.junit.Test;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

/**
 *
 * @author helionagamachi
 */
public class ArrayUtilTest {

    public ArrayUtilTest() {
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
    public void Conversion() {
        TestConversion("+");
        TestConversion("if");
        TestConversion("++");
        TestConversion("+=");
        TestConversion(";");
        TestConversion("while");
        TestConversion("else");
        TestConversion("void");
    }

    public void TestConversion(String word){
        int index;
        index = ArraysUtils.getReservedWordIndex(word);
        assertEquals(word, ArraysUtils.getReservedWordByIndex(index));

    }

    
}
