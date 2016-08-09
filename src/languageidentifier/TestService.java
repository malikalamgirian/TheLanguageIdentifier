/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package languageidentifier;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Provides functionality related to testing the application using the user
 * provided file. Each trained term vector is measured for similarity with test
 * file term vector using a multi-threaded approach.
 *
 * @author Wasif Altaf
 */
public class TestService {

    private String testFolder = null;
    private String trainingFolder = null;
    private String testFileNameAndExtension = null;
    private ArrayList<String> trainingFileNames = null;
    private String trainedFilePrefix = null;
    private SortedMap<Float, String> similarityMap = null;
    private ExecutorService executor = null;

    /**
     * Instantiates Test class given user's test file and folder.
     *
     * @param _testFolder
     * @param _testFileNameAndExtension
     */
    public TestService(String _testFolder, String _testFileNameAndExtension) {
        this.testFolder = _testFolder;
        trainingFolder = PropertiesManager.getInstance().getProperty("train.dev.folder");

        this.testFileNameAndExtension = _testFileNameAndExtension;

        trainingFileNames = new ArrayList<>(Utility.getTrainingFileNames());

        trainedFilePrefix = PropertiesManager.getInstance().getProperty("trained.file.prefix");
        similarityMap = Collections.synchronizedSortedMap(new TreeMap<>());

        executor = Executors.newFixedThreadPool(trainingFileNames.size());
    }

    /**
     * Performs the actual testing operations by reading user input file,
     * converting it to term vector, calculating the dot product of test file
     * term vector with all the training files' term vectors. The higher the dot
     * product, the larger the similarity of input document language to the
     * training file language. Sorting the similarity map in descending based on
     * similarity value, prints the similarity map with highest similarity
     * first.
     *
     * @return true if successful, false if failure.
     */
    public boolean test() {
        boolean status = false;
        Path filePath = null;
        List<String> allLines;
        TermVector testTermVector = new TermVector();
        TermVector trainingTermVector = null;
        Callable<Entry<Float, String>> testCallable = null;
        List<Future<Entry<Float, String>>> testingResults = new ArrayList<>();

        try {
            System.out.println("Reading: " + testFileNameAndExtension);

            allLines = Utility.readAllLines(getTestFolder(), testFileNameAndExtension);

            // case normalize, tokenize, clean tokens, count term frequencies
            if (!testTermVector.populate(allLines)) {
                throw new Exception("Populate failed for " + testFileNameAndExtension + ".");
            } else {
                System.out.println("Finalized preprocessing: " + testFileNameAndExtension);
            }

            // calculate dot product with each trained file using callable
            for (String trainingFileNameAndExtension : trainingFileNames) {
                testCallable = new TestService.Test(trainingFolder,
                        trainedFilePrefix,
                        trainingFileNameAndExtension,
                        testTermVector);

                Future<Entry<Float, String>> future = executor.submit(testCallable);

                testingResults.add(future);
            }

            // populate similarity score map
            for (Future<Entry<Float, String>> future : testingResults) {
                similarityMap.put(future.get().getKey(), future.get().getValue());
            }

            System.out.println(getTestFileNameAndExtension() + " is ");

            // sort similarity scores descendingly
            Comparator<Entry<Float, String>> byKey = (entry1, entry2) -> entry1.getKey().compareTo(
                    entry2.getKey());

            similarityMap
                    .entrySet()
                    .stream()
                    .sorted(byKey.reversed())
                    .forEach(e -> System.out.println(e.getKey() + " " + e.getValue()));

            status = true;
        } catch (InvalidPathException | IOException e) {
            status = false;
            System.err.println(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            status = false;
            System.err.println(e.getMessage());
            e.printStackTrace();
        } finally {
            if (!executor.isTerminated()) {
                System.err.println("cancel non-finished tasks");
            }
            executor.shutdownNow();
            System.out.println("shutdown finished");
        }
        return status;
    }

    public String getTestFolder() {
        return testFolder;
    }

    public String getTestFileNameAndExtension() {
        return testFileNameAndExtension;
    }

    public String getTrainingFolder() {
        return trainingFolder;
    }

    public ArrayList<String> getTrainingFileNames() {
        return trainingFileNames;
    }

    public String getTrainedFilePrefix() {
        return trainedFilePrefix;
    }

    public SortedMap<Float, String> getSimilarityMap() {
        return similarityMap;
    }

    /**
     * Provides functionality related to testing the user provided input file's
     * term vector against the trained file, given the trained file and folder
     * path.
     */
    private class Test implements Callable<Entry<Float, String>> {

        private String trainingFolder = null;
        private String trainedFileNameAndExtension = null;
        private String trainedFilePrefix = null;
        private TermVector testTermVector = null;

        public Test(final String trainingFolder,
                final String trainedFilePrefix,
                final String trainedFileNameAndExtension,
                final TermVector testTermVector) {
            this.trainingFolder = trainingFolder;
            this.trainedFileNameAndExtension = trainedFileNameAndExtension;
            this.trainedFilePrefix = trainedFilePrefix;
            this.testTermVector = testTermVector;
        }

        /**
         * Provides functionality related to testing the user provided input
         * file's term vector against the trained file, given the trained file
         * and folder path.
         *
         * @return An entry with Float dot product of trained and text term
         * vectors as key, and String trained file name as value.
         * 
         * @throws Exception
         */
        @Override
        public Entry<Float, String> call() throws Exception {
            TermVector trainingTermVector = null;
            Entry<Float, String> entryToReturn = null;

            try {
                // load training term vector and perform vector product
                System.out.println("Reading: " + trainedFileNameAndExtension);

                trainingTermVector = new TermVector(Utility.load(
                        trainingFolder,
                        trainedFilePrefix + trainedFileNameAndExtension));

                // calculate dot product
                entryToReturn = new AbstractMap.SimpleEntry(
                        TermVector.dotProduct(trainingTermVector, this.testTermVector),
                        trainedFileNameAndExtension);

            } catch (InvalidPathException e) {
                entryToReturn = null;
                System.err.println(e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                entryToReturn = null;
                System.err.println(e.getMessage());
                e.printStackTrace();
            }

            return entryToReturn;
        }
    }

}
