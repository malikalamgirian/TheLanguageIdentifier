/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package languageidentifier;

import java.util.ArrayList;
import java.util.List;
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
public class PreprocessorTest {
    
    public PreprocessorTest() {
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
     * Test of clean method, of class Preprocessor.
     */
    @Test
    public void testClean_StringArr() {
        System.out.println("clean");
        String[] tokens = {"'hi", "hi'", "h'i"};
        List<String> expResult = new ArrayList<>();
        expResult.add("hi");
        expResult.add("hi");
        expResult.add("h'i");
                
        List<String> result = Preprocessor.clean(tokens);
        assertEquals(expResult, result);
        assertEquals(expResult.size(), result.size());
    }

    /**
     * Test of isValidToken method, of class Preprocessor.
     */
    @Test
    public void testIsValidToken() {
        System.out.println("isValidToken");
        String token = "";
        boolean expResult = false;
        boolean result = Preprocessor.isValidToken(token);
        assertEquals(expResult, result);
        
        token = null;
        expResult = false;
        result = Preprocessor.isValidToken(token);
        assertEquals(expResult, result);
        
        token = "hello";
        expResult = true;
        result = Preprocessor.isValidToken(token);
        assertEquals(expResult, result);        
    }

    /**
     * Test of clean method, of class Preprocessor.
     */
    @Test
    public void testClean_String() {
        System.out.println("clean");
        String token = "'h'i'";
        String expResult = "h'i";
        String result = Preprocessor.clean(token);
        assertEquals(expResult, result);
        
        token = "";
        expResult = "";
        result = Preprocessor.clean(token);
        assertEquals(expResult, result);
                
        token = "!hi";
        expResult = "hi";
        result = Preprocessor.clean(token);
        assertEquals(expResult, result);
    }

    /**
     * Test of tokenize method, of class Preprocessor.
     */
    @Test
    public void testTokenize() {
        System.out.println("tokenize");
        String line = "hi how are you";
        String[] expResult = {"hi", "how", "are", "you"};
        String[] result = Preprocessor.tokenize(line);
        
        assertArrayEquals(expResult, result);
    }    
}
