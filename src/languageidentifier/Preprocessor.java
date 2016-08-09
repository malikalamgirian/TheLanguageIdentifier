/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package languageidentifier;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Provides basic preprocessing functionality like tokenization, cleaning etc.
 * 
 * @author Wasif Altaf
 */
public class Preprocessor {
  
    /**
     * Cleans tokens.
     *
     * @param tokens Tokens to clean
     * @return Cleaned tokens
     */
    public static List<String> clean(String[] tokens) {
        List<String> cleanTokens = null;
        String cleaned = null;

        try {
            if (tokens == null) {
                throw new NullPointerException("Array of tokens is null.");
            } else {
                cleanTokens = new ArrayList<>();
                
                for (int i = 0; i < tokens.length; i++) {
                    cleaned = clean(tokens[i]);
                    if (isValidToken(cleaned)) {
                        cleanTokens.add(cleaned);
                    }
                }
            }
        } catch (NullPointerException e) {
            System.err.println("Exception in clean");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Exception in clean");
            e.printStackTrace();
        }

        return cleanTokens;
    }

    /**
     * Tests the validity of token.
     *
     * @param token Token to test for validity
     * @return true if token is valid, false otherwise
     */
    public static boolean isValidToken(String token) {
        if (token == null
                || token.equals("")
                || token.length() == 0) {
            return false;
        }
        return true;
    }

    /**
     * Cleans the token by removing the prefix or suffix punctuation.
     *
     * @param token Token to clean
     * @return Cleaned token
     */
    public static String clean(String token) {
        try {
            if (!isValidToken(token)) {
                return token;
            }

            // remove starting punctuation if any
            if (Pattern.matches("\\p{Punct}", token.subSequence(0, 1))) {
                token = token.substring(1);
            }

            if (!isValidToken(token)) {
                return token;
            }

            // remove trailing punctuation if any
            if (Pattern.matches("\\p{Punct}",
                    token.subSequence(token.length() - 1, token.length()))) {
                token = token.substring(0, token.length() - 1);
            }
        } catch (PatternSyntaxException | NullPointerException e) {
            System.err.println("Exception in clean");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Exception in clean");
            e.printStackTrace();
        }

        return token;
    }

    /**
     * Tokenizes the provided line on the base of white space character.
     *
     * @param line String to tokenize
     * @return Tokenized string, null in case of failure
     */
    public static String[] tokenize(String line) {
        String[] tokens = null;

        try {
            tokens = line.split("\\s");

        } catch (PatternSyntaxException | NullPointerException e) {
            tokens = null;
            System.err.println("Exception in tokenize");
            e.printStackTrace();
        } catch (Exception e) {
            tokens = null;
            System.err.println("Exception in tokenize");
            e.printStackTrace();
        }

        return tokens;
    }
}
