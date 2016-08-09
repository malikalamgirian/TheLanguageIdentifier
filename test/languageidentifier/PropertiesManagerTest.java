/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package languageidentifier;

import java.util.Set;
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
public class PropertiesManagerTest {
    
    public PropertiesManagerTest() {
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
     * Test of getInstance method, of class PropertiesManager.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        PropertiesManager expResult = PropertiesManager.getInstance();
        PropertiesManager result = PropertiesManager.getInstance();
        assertEquals(expResult, result);
    }

    /**
     * Test of getProperty method, of class PropertiesManager.
     */
    @Test
    public void testGetProperty() {
        System.out.println("getProperty");
        String key = "train.danish";
        PropertiesManager instance = PropertiesManager.getInstance();
        String expResult = "danish.txt";
        String result = instance.getProperty(key);
        
        assertEquals(expResult, result);
        
        key = "";
        result = instance.getProperty(key);
        assertEquals(null, result);
    }

    /**
     * Test of getAllPropertyNames method, of class PropertiesManager.
     */
    @Test
    public void testGetAllPropertyNames() {
        System.out.println("getAllPropertyNames");
        PropertiesManager instance = PropertiesManager.getInstance();
        Set<String> expResult = null;
        Set<String> result = instance.getAllPropertyNames();
        
        assertNotEquals(expResult, result);
        assertTrue(result.size() > 10);
    }

    /**
     * Test of containsKey method, of class PropertiesManager.
     */
    @Test
    public void testContainsKey() {
        System.out.println("containsKey");
        String key = "";
        PropertiesManager instance = PropertiesManager.getInstance();
        boolean expResult = false;
        boolean result = instance.containsKey(key);
        
        assertEquals(expResult, result);
    }    
}