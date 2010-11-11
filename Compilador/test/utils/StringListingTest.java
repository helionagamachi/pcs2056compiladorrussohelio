/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author helionagamachi
 */
public class StringListingTest {

    StringListing list;
    
    public StringListingTest() {
    }

    @Before
    public void setUp() {
        list = new StringListing();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void arrayTest(){
        String[] array;
        array = list.getArray();
        assertEquals("No elements on the array is expected",0, array.length);
        list.addString("Testme!");
        list.addString("Testme2!");
        array = list.getArray();
        assertEquals("Should have 2 elements" , 2, array.length);
        assertEquals(array[0], "Testme!");
        assertEquals(array[1], "Testme2!");
    }

    @Test
    public void indexTest(){
        list.addString("Test1");
        list.addString("Test2");
        list.addString("Test3");
        assertEquals(list.getByNumber(0), "Test1");
        assertEquals(list.getByNumber(1), "Test2");
        assertEquals(list.getByNumber(2), "Test3");
    }

    @Test
    public void getIndexTest(){
        list.addString("Test1");
        list.addString("Test2");
        list.addString("Test3");
        list.addString("Test1");
        assertEquals(list.getId("Test1"), 0);
        assertEquals(list.getId("Test2"), 1);
        assertEquals(list.getId("Test3"), 2);
    }

}