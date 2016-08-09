/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package languageidentifier;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
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
public class UtilityTest {

    public UtilityTest() {
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
     * Test of save method, of class Utility.
     */
    @Test
    public void testSave() {
        System.out.println("save");
        ConcurrentHashMap<String, Float> map = new ConcurrentHashMap<>();

        map.put("hi", 1.1f);
        map.put("love", 2.2f);

        String folderPath = "C:\\Users\\Wasif Altaf\\Documents\\";
        String fileNameAndExtension = "UtilityTest.text";

        boolean expResult = true;
        boolean result = Utility.save(map, folderPath, fileNameAndExtension);

        assertEquals(expResult, result);
        assertTrue(Files.exists(Paths.get(folderPath, fileNameAndExtension)));
    }

    /**
     * Test of load method, of class Utility.
     */
    @Test
    public void testLoad() {
        System.out.println("load");
        String folderPath = "resources/train/";
        String fileNameAndExtension = "pp_danish.txt";
        ConcurrentHashMap expResult = new ConcurrentHashMap<>();

        ConcurrentHashMap result = Utility.load(folderPath, fileNameAndExtension);

        assertTrue(result.size() > 10);
    }

    /**
     * Test of getTrainingFileNames method, of class Utility.
     */
    @Test
    public void testGetTrainingFileNames() {
        System.out.println("getTrainingFileNames");
        List<String> expResult = new ArrayList<>();

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

        List<String> result = Utility.getTrainingFileNames();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFolderPath method, of class Utility.
     */
    @Test
    public void testGetFolderPath() {
        System.out.println("getFolderPath");
        File file = Paths.get("C:\\Users\\Wasif Altaf\\Documents\\", "UtilityTest.text").toFile();
        String expResult = "C:\\Users\\Wasif Altaf\\Documents\\";
        String result = Utility.getFolderPath(file);
        
        assertEquals(expResult, result);
    }

    /**
     * Test of readAllLines method, of class Utility.
     */
    @Test
    public void testReadAllLines() {
        System.out.println("readAllLines");
        String folderPath = "C:\\Users\\Wasif Altaf\\Documents\\languageIdentifier\\test";
        String fileNameAndExtension = "text_test.txt";
        List<String> expResult = new ArrayList<>();
        
        expResult.add("hi");
        expResult.add("hello");
        expResult.add("how are you");
        
        List<String> result = Utility.readAllLines(folderPath, fileNameAndExtension);
        assertEquals(expResult, result);
    }
}