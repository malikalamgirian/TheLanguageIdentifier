/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package languageidentifier;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Provides functionality related to training the language identifier using
 * training files. Performs the training in multi-threaded way. 
 *
 * @author Wasif Altaf
 */
public class TrainService {

    private String trainingFolder = null;
    private ArrayList<String> fileNames = null;
    private String trainedFilePrefix = null;
    ExecutorService executor = null;

    public TrainService() {
        fileNames = new ArrayList<>(Utility.getTrainingFileNames());

        trainingFolder = PropertiesManager.getInstance().getProperty("train.dev.folder");
        trainedFilePrefix = PropertiesManager.getInstance().getProperty("trained.file.prefix");

        executor = Executors.newFixedThreadPool(fileNames.size());
    }

    /**
     * Performs the actual training operations by reading training files,
     * converting each training file to term vector. The term vectors are
     * normalized to percentages based frequencies. The post processed term
     * vectors are then saved to disk for testing purposes.
     *
     * @return true if successful, false otherwise
     */
    public boolean train() {
        boolean status = true;
        List<Future<Boolean>> trainingResults = new ArrayList<>();
        Callable<Boolean> trainingCallable = null;

        try {
            // load each file and pre-train (case normalize, tokenize etc)
            for (String fileNameAndExtension : fileNames) {

                trainingCallable = new Train(trainingFolder, fileNameAndExtension, 
                        trainedFilePrefix);
                Future<Boolean> future = executor.submit(trainingCallable);

                trainingResults.add(future);
            }

            // print training results
            for (Future<Boolean> future : trainingResults) {
                if (!future.get()) {
                    status = false;
                }
                System.out.println(new Date() + "::" + future.get());
            }

        } catch (InterruptedException | ExecutionException e) {
            status = false;
            e.printStackTrace();
        } catch (Exception e) {
            status = false;
            System.err.println("Exception in train process");
            e.printStackTrace();
        } finally {
            if (!executor.isTerminated()) {
                System.err.println("Cancel non-finished tasks");
            }
            executor.shutdownNow();
            System.out.println("Shutdown finished");
        }

        return status;
    }

    /**
     * Accessor for getting all training file names.
     *
     * @return list of all training file names
     */
    public ArrayList<String> getFileNames() {
        return fileNames;
    }

    /**
     * Accessor for training folder path
     *
     * @return training folder path
     */
    public String getTrainingFolder() {
        return trainingFolder;
    }

    /**
     * Accessor for trained file prefix
     *
     * @return the trained file prefix used in trained file naming
     */
    public String getTrainedFilePrefix() {
        return trainedFilePrefix;
    }

    /**
     * Performs the actual training operations by reading training file on a Callable,
     * converting the training file to term vector. The term vectors are
     * normalized to percentages based frequencies. The post processed term
     * vectors are then saved to disk for testing purposes.
     */
    private class Train implements Callable<Boolean> {

        private String trainingFolder = null;
        private String fileNameAndExtension = null;
        private String trainedFilePrefix = null;

        public Train(String trainingFolder, String fileNameAndExtension, String trainedFilePrefix) {
            this.trainingFolder = trainingFolder;
            this.fileNameAndExtension = fileNameAndExtension;
            this.trainedFilePrefix = trainedFilePrefix;
        }

        /**
         * Performs the actual training operations by reading training file,
         * converting the training file to term vector. The term vectors are
         * normalized to percentages based frequencies. The post processed term
         * vectors are then saved to disk for testing purposes. Returns boolean
         *
         * @return true if training file has been successfully processed, false otherwise
         * @throws Exception 
         */
        @Override
        public Boolean call() throws Exception {
            boolean status = false;
            boolean saved = false;
            Path filePath = null;
            List<String> allLines;
            TermVector termVector = new TermVector();

            try {
                System.out.println("Reading: " + fileNameAndExtension);

                // load file and pre-train (case normalize, tokenize etc)
                allLines = Utility.readAllLines(this.trainingFolder, this.fileNameAndExtension);

                System.out.println("Read: " + fileNameAndExtension);

                // case normalize, tokenize, clean tokens, count term frequencies
                if (!termVector.populate(allLines)) {
                    throw new Exception("Populate failed for " + fileNameAndExtension + ".");
                } else {
                    System.out.println("Counted term frequencies: " + fileNameAndExtension);
                }

                // convert term frequencies to percentage
                if (!termVector.normalizeTermFrequencies()) {
                    throw new Exception("Could not normalize " + fileNameAndExtension + ".");
                } else {
                    System.out.println("Converted to percentages for : " + termVector.getTermVector().size() + " unique termed "
                            + fileNameAndExtension);
                }

                // save term freq map
                saved = Utility.save(termVector.getTermVector(),
                        this.trainingFolder, this.trainedFilePrefix + this.fileNameAndExtension);

                if (saved) {
                    termVector.getTermVector().clear();
                    System.out.println("Saved");
                } else {
                    throw new IOException("Could not save file " + fileNameAndExtension + ".");
                }

                status = true;
            } catch (NoSuchFileException e) {
                status = false;
                System.err.println(e.getMessage());
                e.printStackTrace();
            } catch (IOException | InvalidPathException e) {
                status = false;
                System.err.println(e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                status = false;
                System.err.println("Exception in train process");
                e.printStackTrace();
            }
            return status;
        }
    }
}
