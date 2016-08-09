/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package languageidentifier;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class TrainServiceTest {
    
    public TrainServiceTest() {
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
     * Test of getFileNames method, of class TrainService.
     */
    @Test
    public void testGetFileNames() {
        System.out.println("getFileNames");
        TrainService instance = new TrainService();
        ArrayList<String> expResult =  new ArrayList<>();

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
        
        ArrayList<String> result = instance.getFileNames();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTrainingFolder method, of class TrainService.
     */
    @Test
    public void testGetTrainingFolder() {
        System.out.println("getTrainingFolder");
        TrainService instance = new TrainService();
        String expResult = "src/resources/train/";
        String result = instance.getTrainingFolder();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTrainedFilePrefix method, of class TrainService.
     */
    @Test
    public void testGetTrainedFilePrefix() {
        System.out.println("getTrainedFilePrefix");
        TrainService instance = new TrainService();
        String expResult = "pp_";
        String result = instance.getTrainedFilePrefix();
        assertEquals(expResult, result);
    }

    /**
     * Test of train method, of class TrainService.
     */
    @Test
    public void testTrain() {
        System.out.println("train");
        TrainService instance = new TrainService();
        boolean expResult = true;
        boolean result = instance.train();
        assertEquals(expResult, result);
    }
    
}
