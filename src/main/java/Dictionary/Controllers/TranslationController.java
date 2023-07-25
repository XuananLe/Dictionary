package Dictionary.Controllers;

import Dictionary.Utils.TranslateManager;
import Dictionary.Utils.VoiceManager;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;

import javax.swing.*;
import java.io.IOException;
import java.util.Dictionary;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TranslationController {

    public TextArea SourceLanguage = new TextArea();
    public TextArea TransalationLanguage = new TextArea();

    public ExecutorService executor = Executors.newSingleThreadExecutor(); // Create an ExecutorService with a single thread

    public void translateWord() {
        String textToTranslate = SourceLanguage.getText();
        if(textToTranslate.isEmpty() || textToTranslate.isBlank())
        {
            TransalationLanguage.setText("");
            return;
        }

        Runnable task = () -> {
            try {
                String sourceLanguage = "en";
                String targetLanguage = "vi";
                String translation = TranslateManager.translateWord(textToTranslate, sourceLanguage, targetLanguage);
                SwingUtilities.invokeLater(() -> {
                    TransalationLanguage.setText(translation);
                });
            } catch (IOException e) {
                // Handle exception
                e.printStackTrace();
            }
        };

        executor.execute(task);
    }

    public void handleVoice() {
        String textToTranslate = SourceLanguage.getText();
        if(textToTranslate.isEmpty() || textToTranslate.isBlank())
        {
            return;
        }
        VoiceManager.playVoice(textToTranslate);
    }
}
