/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package languageidentifier;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Term Vector with term frequencies and relevant convenience methods for processing
 * term vectors.
 *
 * @author Wasif Altaf
 */
public class TermVector implements Serializable {

    private ConcurrentHashMap<String, Float> termVector;

    /**
     *
     */
    public TermVector() {
        termVector = new ConcurrentHashMap<>();
    }

    /**
     * Creates Term Vector from the provided HashMap.
     *
     * @param termVector Term Vector as Hash Map of Strings and frequencies (as
     * floats)
     */
    public TermVector(ConcurrentHashMap<String, Float> termVector) {
        this.termVector = termVector;
    }

    /**
     * Creates Term Vector from the provided List of Strings.
     *
     * @param allLines list of Strings to create term vector from.
     * @throws InstantiationException In case populate is not successful
     * InstantiationException is thrown
     */
    public TermVector(List<String> allLines) throws InstantiationException {
        termVector = new ConcurrentHashMap<>();

        if (!populate(allLines)) {
            throw new InstantiationException("Could not instantiate. "
                    + "Check input parameter.");
        }
    }

    /**
     * Normalizes term frequencies to percentage values based on sum of term
     * frequencies of all terms contained in the term vector.
     *
     * @return true if successful, false if normalization fails
     */
    public boolean normalizeTermFrequencies() {
        float totalNumberOfTerms = 0;
        boolean status = false;

        try {
            // find total number of term frequencies
            totalNumberOfTerms = getTermFrequenciesSum();

            // convert term frequencies to percentage
            for (Map.Entry<String, Float> entry : this.termVector.entrySet()) {
                String key = entry.getKey();
                float value = entry.getValue();
                this.termVector.replace(key, value, value * 100 / totalNumberOfTerms);
            }
            status = true;
        } catch (IllegalStateException e) {
            status = false;
            System.err.println("IllegalStateException in normalizeTermFrequencies");
            e.printStackTrace();
        } catch (Exception e) {
            status = false;
            System.err.println("Exception in normalizeTermFrequencies");
            e.printStackTrace();
        }
        return status;
    }

    /**
     * Find sum of frequencies of all terms contained in term vector, or
     * negative value if failure
     *
     * @return Sum of frequencies of all terms contained in term vector, or
     * negative value if failure
     */
    public float getTermFrequenciesSum() {
        float sum = 0;

        try {
            for (Map.Entry<String, Float> entry : termVector.entrySet()) {
                String key = entry.getKey();
                Float value = entry.getValue();

                sum += value;
            }
        } catch (IllegalStateException e) {
            sum = -1;
            System.err.println("IllegalStateException in sumTermFrequencies");
            e.printStackTrace();
        } catch (Exception e) {
            sum = -1;
            System.err.println("Exception in sumTermFrequencies");
            e.printStackTrace();
        }
        return sum;
    }

    /**
     * Populates the term vector using the list of strings provided as
     * parameter. Normalizes the case of strings to lower case. Tokenizes
     * strings on the basis of white space. Cleans tokens by removing prefix and
     * suffix punctuation. Then populates the term vector using term
     * frequencies.
     *
     * @param allLines list of strings to be populated into term vector.
     * @return true if list of strings is successfully populated, returns false
     * otherwise.
     */
    public boolean populate(List<String> allLines) {
        boolean status = false;
        String[] tokens = null;
        List<String> cleanedTokens = null;

        try {
            if (allLines == null) {
                throw new NullPointerException("Parameter list of strings is null.");
            } else {
                for (String line : allLines) {
                    // case normalize
                    line = line.toLowerCase();

                    // tokenize 
                    tokens = Preprocessor.tokenize(line);

                    // clean tokens
                    cleanedTokens = Preprocessor.clean(tokens);

                    // count term frequencies
                    for (String token : cleanedTokens) {
                        if (this.termVector.containsKey(token)) {
                            // increment the value against token
                            this.termVector.replace(token, this.termVector.get(token) + 1);
                        } else {
                            this.termVector.put(token, 1f);
                        }
                    }
                }
            }
            status = true;
        } catch (NullPointerException e) {
            status = false;
            System.err.println("NullPointerException in populate");
            System.err.println(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            status = false;
            System.err.println(e.getMessage());
            System.err.println("Exception in populate");
            e.printStackTrace();
        }
        return status;
    }

    /**
     * Calculates the dot product of two term vectors and returns result as
     * float.
     *
     * @param o1 first term vector
     * @param o2 second term vector
     * @return product of o1 and o2 term vectors if successful, negative value
     * in case of product calculation fails.
     */
    public static float dotProduct(TermVector o1,
            TermVector o2) {
        float product = 0;

        try {
            if (o1 == null) {
                throw new NullPointerException("o1 is null");
            } else if (o1.getTermVector() == null) {
                throw new NullPointerException("o1's map is null");
            } else if (o2 == null) {
                throw new NullPointerException("o2 is null");
            } else if (o2.getTermVector() == null) {
                throw new NullPointerException("o2's map is null");
            }

            for (Map.Entry<String, Float> entry : o1.getTermVector().entrySet()) {
                String key = entry.getKey();
                Float value = entry.getValue();

                if (o2.getTermVector().containsKey(key)) {
                    product += o2.getTermVector().get(key) * value;

                    System.out.println(key + " has value "
                            + o2.getTermVector().get(key) + " * " + value
                            + " = " + (o2.getTermVector().get(key) * value)
                    );
                }
            }
        } catch (NullPointerException e) {
            product = -1;
            System.err.println("NullPointerException in dotProduct");
            System.err.println(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            product = -1;
            System.out.println("Exception in dotProduct");
            e.printStackTrace();
        }
        System.out.println("Final : " + product);

        return product;
    }

    /**
     * Term vector accessor
     *
     * @return the term vector Hash Map
     */
    public ConcurrentHashMap<String, Float> getTermVector() {
        return termVector;
    }

    /**
     * Term vector mutator
     *
     * @param termVector Term vector as Hash Map
     */
    public void setTermVector(ConcurrentHashMap<String, Float> termVector) {
        this.termVector = termVector;
    }
}
