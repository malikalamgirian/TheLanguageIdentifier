/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package languageidentifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Provides utility functionality through various convenience methods used for
 * language identification.
 *
 * @author Wasif Altaf
 */
public class Utility {

    /**
     * Saves the map to disk.
     *
     * @param map map to save to disk.
     * @param folderPath path to folder.
     * @param fileNameAndExtension file name and extension where map where be
     * saved
     * @return true if save successful, otherwise false
     */
    public static boolean save(ConcurrentHashMap map, String folderPath, String fileNameAndExtension) {
        boolean status = false;
        Path filePath = null;

        try {
            // save map using Serialization based approach
            filePath = FileSystems.getDefault().getPath(folderPath, fileNameAndExtension);
            OutputStream os = Files.newOutputStream(filePath);
            ObjectOutputStream oos = new ObjectOutputStream(os);

            oos.writeObject(map);

            // close streams
            oos.close();
            os.close();

            status = true;
        } catch (IOException e) {
            status = false;
            System.err.println("IOException in save.");
            e.printStackTrace();
        } catch (SecurityException e) {
            status = false;
            System.err.println("SecurityException in save.");
            e.printStackTrace();
        } catch (Exception e) {
            status = false;
            System.err.println("Exception in save.");
            e.printStackTrace();
        }

        return status;
    }

    /**
     * Loads Hash Map from provided folder and file with JAR given as
     * parameters.
     *
     * @param folderPath folder path of the file
     * @param fileNameAndExtension name and extension of file
     * @return HashMap read from file if reading is successful, otherwise null
     */
    public static ConcurrentHashMap load(String folderPath, String fileNameAndExtension) {
        ConcurrentHashMap<String, Float> map = null;

        try {
            InputStream is = Utility.class.getClassLoader()
                    .getResourceAsStream(folderPath + fileNameAndExtension);

            if (is == null) {
                is = Files.newInputStream(Paths.get(folderPath + fileNameAndExtension));
            }

            if (is == null) {
                throw new NullPointerException("Input steam is null. Check path " + folderPath + fileNameAndExtension);
            }

            ObjectInputStream ois = new ObjectInputStream(is);
            map = (ConcurrentHashMap<String, Float>) ois.readObject();

            ois.close();
            is.close();
        } catch (NullPointerException e) {
            map = null;
            System.err.println("NullPointerException in load.");
            System.err.println(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            map = null;
            System.err.println("IOException in load.");
            System.err.println(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            map = null;
            System.err.println("Exception in load.");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    /**
     *
     * @param folderPath
     * @param fileNameAndExtension
     * @return
     */
    public static List<String> readAllLines(String folderPath, String fileNameAndExtension) {
        List<String> allLines = new ArrayList<>();
        Path filePath = null;
        InputStream is = null;
        BufferedReader br = null;

        try {
            filePath = Paths.get(folderPath, fileNameAndExtension);

            if (!Files.exists(filePath)) {
                is = Utility.class.getClassLoader()
                        .getResourceAsStream(folderPath + fileNameAndExtension);

                if (is == null) {
                    is = Files.newInputStream(Paths.get(folderPath + fileNameAndExtension));
                }

                if (is == null) {
                    throw new NullPointerException("Input steam is null. Check path " + folderPath + fileNameAndExtension);
                }

                br = new BufferedReader(new InputStreamReader(is));

                for (;;) {
                    String line = br.readLine();
                    if (line == null) {
                        break;
                    }
                    allLines.add(line);
                }

                is.close();
            } else {
                allLines = Files.readAllLines(filePath, StandardCharsets.ISO_8859_1);
            }

        } catch (NullPointerException e) {
            allLines = null;
            System.err.println("NullPointerException in readAllLines.");
            System.err.println(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            allLines = null;
            System.err.println("IOException in readAllLines.");
            System.err.println(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            allLines = null;
            System.err.println("Exception in readAllLines.");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return allLines;
    }

    /**
     * Looks up training file names from config properties file.
     *
     * @return list of strings as result
     */
    public static List<String> getTrainingFileNames() {
        List<String> fileNames = null;

        try {
            fileNames = new ArrayList();

            fileNames.add(PropertiesManager.getInstance().getProperty("train.danish"));
            fileNames.add(PropertiesManager.getInstance().getProperty("train.dutch"));
            fileNames.add(PropertiesManager.getInstance().getProperty("train.english"));
            fileNames.add(PropertiesManager.getInstance().getProperty("train.finnish"));
            fileNames.add(PropertiesManager.getInstance().getProperty("train.french"));
            fileNames.add(PropertiesManager.getInstance().getProperty("train.german"));
            fileNames.add(PropertiesManager.getInstance().getProperty("train.italian"));
            fileNames.add(PropertiesManager.getInstance().getProperty("train.portugese"));
            fileNames.add(PropertiesManager.getInstance().getProperty("train.spanish"));
            fileNames.add(PropertiesManager.getInstance().getProperty("train.swedish"));
            fileNames.add(PropertiesManager.getInstance().getProperty("train.urdu"));
        } catch (NullPointerException e) {
            fileNames = null;
            System.err.println("NullPointerException in getting training file names from config properties.");
            System.err.println(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            fileNames = null;
            System.err.println("Exception in getting training file names from config properties.");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return fileNames;
    }

    /**
     * Finds folder path for the given file.
     *
     * @param file file for which to find the folder name
     * @return the folder path if found, null if folder path not found or does
     * not exist
     */
    public static String getFolderPath(File file) {
        String folderPath = null;

        try {
            if (!file.isFile()) {
                throw new FileNotFoundException("Not a valid file.");
            } else {
                folderPath = file.getPath().substring(0,
                        file.getPath().indexOf(file.getName()));
            }
        } catch (IndexOutOfBoundsException e) {
            folderPath = null;
            System.err.println(e.getMessage());
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            folderPath = null;
            System.err.println(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            folderPath = null;
            e.printStackTrace();
        }
        return folderPath;
    }
}
