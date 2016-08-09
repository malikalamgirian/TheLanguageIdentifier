/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package languageidentifier;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

/**
 * Provides basic functionality for managing the configuration properties.
 * 
 * @author Wasif Altaf
 *
 * Based on the idea from 
 * http://howtodoinjava.com/core-java/io/java-loadreadwrite-properties-file-examples/
 */
public final class PropertiesManager {

    private final Properties properties = new Properties();

    /**
     * Instantiate properties manager for creating configuration properties. 
     * 
     */
    private PropertiesManager() {
        InputStream input = null;

        try {
            input = this.getClass().getClassLoader()
                    .getResourceAsStream("resources/config.properties");

            if (input == null) {
                throw new NullPointerException("Could not load propoerties file.");
            }

            properties.load(input);

        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.err.println("Propoerties file not found.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Could not load due to IOException.");
            e.printStackTrace();
        }
    }

    /**
     * Static class to hold and manage the properties manager instance
     */
    private static class LazyHolder {
        private static final PropertiesManager INSTANCE = new PropertiesManager();
    }

    /**
     * To access properties manager instance.
     * 
     * @return Properties manager instance. 
     */
    public static PropertiesManager getInstance() {
        return LazyHolder.INSTANCE;
    }

    /**
     * Gets property value based on the key.
     * 
     * @param key Key for which value is to be retrieved
     * @return Value for key if found, null if value not found.
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Retrieves names of all properties as Strings.
     * 
     * @return Names of all properties as Set of Strings.
     */
    public Set<String> getAllPropertyNames() {
        return properties.stringPropertyNames();
    }

    /**
     * Tests whether there exists a property with the provided key.
     * 
     * @param key Key for which to test existence
     * @return true if key is found, false if not found
     * @throws NullPointerException if the key is <code>null</code>
     */
    public boolean containsKey(String key) throws NullPointerException {
        return properties.containsKey(key);
    }
}