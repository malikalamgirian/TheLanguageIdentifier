package languageidentifier;

import java.awt.Color;
import javax.swing.JFrame;
import languageidentifier.forms.LanguageIdentifierForm;

/**
 * Main class for The Language Identifier. Performs training, and creates
 * input file form.
 * 
 * @author Wasif Altaf
 */
public class LanguageIdentifier {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        // perform training 
        TrainService train = new TrainService();

        System.out.println("Training starting");
        train.train();

        // create and show main input form
        LanguageIdentifierForm mainForm
                = new LanguageIdentifierForm();

        mainForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainForm.setBackground(Color.WHITE);
        mainForm.setLocationRelativeTo(null);
        mainForm.setResizable(false);

        mainForm.setVisible(true);
    }
}
