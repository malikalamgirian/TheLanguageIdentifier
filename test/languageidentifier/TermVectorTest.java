/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package languageidentifier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Wasif Altaf
 */
public class TermVectorTest {

    public TermVectorTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of normalizeTermFrequencies method, of class TermVector.
     */
    @Test
    public void testNormalizeTermFrequencies() {
        System.out.println("normalizeTermFrequencies");
        TermVector instance = new TermVector();
        assertNotNull(instance.getTermVector());

        ConcurrentHashMap map = instance.getTermVector();
        assertNotNull(map);

        map.put("hi", 1.0f);
        map.put("hello", 2.0f);

        instance = new TermVector(map);

        boolean expResult = true;
        boolean result = instance.normalizeTermFrequencies();

        assertEquals(expResult, result);
        assertEquals(instance.getTermVector().get("hi"), 33.33f, .5);
        assertEquals(instance.getTermVector().get("hello"), 66.33f, .5);
    }

    /**
     * Test of getTermFrequenciesSum method, of class TermVector.
     */
    @Test
    public void testGetTermFrequenciesSum() {
        System.out.println("getTermFrequenciesSum");
        TermVector instance = new TermVector();
        assertNotNull(instance.getTermVector());

        ConcurrentHashMap map = instance.getTermVector();
        assertNotNull(map);

        map.put("hi", 1f);
        map.put("hello", 2f);

        instance = new TermVector(map);

        float expResult = 3.0F;
        float result = instance.getTermFrequenciesSum();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of populate method, of class TermVector.
     */
    @Test
    public void testPopulate() {
        System.out.println("populate");
        List<String> allLines = new ArrayList<>();

        allLines.add("hi how are you");
        allLines.add("hello how are you");

        TermVector instance = new TermVector();
        boolean expResult = true;
        boolean result = instance.populate(allLines);

        assertEquals(expResult, result);
        assertEquals(5, instance.getTermVector().size());
    }

    /**
     * Test of dotProduct method, of class TermVector.
     */
    @Test
    public void testDotProduct() {
        System.out.println("dotProduct");
        TermVector o1 = null;
        TermVector o2 = null;
        float expResult = -1.0F;
        float result = 0;

        List<String> allLines = new ArrayList<>();
        allLines.add("hi how are you");

        try {
            o1 = new TermVector(new ArrayList<>(allLines));
        } catch (InstantiationException ex) {
            Logger.getLogger(TermVectorTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        allLines.add("hello how are you");

        try {
            o2 = new TermVector(new ArrayList<>(allLines));
        } catch (InstantiationException ex) {
            Logger.getLogger(TermVectorTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        result = TermVector.dotProduct(o1, o2);
        expResult = 7.0f;
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getTermVector method, of class TermVector.
     */
    @Test
    public void testGetTermVector() {
        System.out.println("getTermVector");
        TermVector instance = new TermVector();
        int expResult = 0;
        ConcurrentHashMap<String, Float> result = instance.getTermVector();
        
        assertEquals(expResult, result.size());
    }

    /**
     * Test of setTermVector method, of class TermVector.
     */
    @Test
    public void testSetTermVector() {
        System.out.println("setTermVector");
        ConcurrentHashMap<String, Float> termVector = new ConcurrentHashMap<>();
        TermVector instance = new TermVector();
        instance.setTermVector(termVector);
        
        assertEquals(instance.getTermVector().size(), 0);
    }

}
