/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package languageidentifier;

import java.util.ArrayList;
import java.util.SortedMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Wasif Altaf
 */
public class TestServiceTest {

    public TestServiceTest() {
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
     * TestService of test method, of class TestService.
     */
    @Test
    public void testTest() {
        System.out.println("test");
        languageidentifier.TestService instance
                = new languageidentifier.TestService("C:\\Users\\Wasif Altaf\\Documents\\languageIdentifier\\test",
                        "text_de.txt");
        boolean expResult = true;
        boolean result = instance.test();
        assertEquals(expResult, result);
    }

    /**
     * TestService of getTrainingFileNames method, of class TestService.
     */
    @Test
    public void testGetTrainingFileNames() {
        System.out.println("getTrainingFileNames");
        languageidentifier.TestService instance = new languageidentifier.TestService("", "");
        ArrayList<String> expResult = new ArrayList<>();

        expResult.add("danish.txt");
        expResult.add("dutch.txt");
        expResult.add("english.txt");
        expResult.add("finnish.txt");
        expResult.add("french.txt");
        expResult.add("german.txt");
        expResult.add("italian.txt");
        expResult.add("portugese.txt");
        expResult.add("spanish.txt");
        expResult.add("swedish.txt");

        ArrayList<String> result = instance.getTrainingFileNames();
        assertEquals(expResult, result);
    }

    /**
     * TestService of getTrainedFilePrefix method, of class TestService.
     */
    @Test
    public void testGetTrainedFilePrefix() {
        System.out.println("getTrainedFilePrefix");
        languageidentifier.TestService instance = new languageidentifier.TestService("", "");
        String expResult = "pp_";
        String result = instance.getTrainedFilePrefix();
        assertEquals(expResult, result);
    }

    /**
     * TestService of getSimilarityMap method, of class TestService.
     */
    @Test
    public void testGetSimilarityMap() {
        System.out.println("getSimilarityMap");
        languageidentifier.TestService instance = new languageidentifier.TestService("", "");
        SortedMap<Float, String> expResult = null;
        SortedMap<Float, String> result = instance.getSimilarityMap();

        assertNotEquals(expResult, result);
        assertEquals(0, result.size());
    }

    /**
     * Test of getTestFolder method, of class TestService.
     */
    @Test
    public void testGetTestFolder() {
        System.out.println("getTestFolder");
        TestService instance = new languageidentifier.TestService("C:\\Users\\Wasif Altaf\\Documents\\languageIdentifier\\test",
                        "text_de.txt");
        String expResult = "C:\\Users\\Wasif Altaf\\Documents\\languageIdentifier\\test";
        String result = instance.getTestFolder();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTestFileNameAndExtension method, of class TestService.
     */
    @Test
    public void testGetTestFileNameAndExtension() {
        System.out.println("getTestFileNameAndExtension");
        TestService instance = new languageidentifier.TestService("C:\\Users\\Wasif Altaf\\Documents\\languageIdentifier\\test",
                        "text_de.txt");
        String expResult = "text_de.txt";
        String result = instance.getTestFileNameAndExtension();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTrainingFolder method, of class TestService.
     */
    @Test
    public void testGetTrainingFolder() {
        System.out.println("getTrainingFolder");
        TestService instance = new languageidentifier.TestService("C:\\Users\\Wasif Altaf\\Documents\\languageIdentifier\\test",
                        "text_de.txt");
        String expResult = "src/resources/train/";
        String result = instance.getTrainingFolder();
        assertEquals(expResult, result);
    }
}
