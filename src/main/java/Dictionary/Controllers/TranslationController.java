package Dictionary.Controllers;

import Dictionary.Utils.TranslateManager;
import Dictionary.Utils.VoiceManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TranslationController {
    @FXML
    public TextArea SourceLanguage;
    @FXML
    public TextArea TranslationLanguage;
    @FXML
    private ComboBox<String> sourceLanguageComboBox;
    @FXML
    private ComboBox<String> targetLanguageComboBox;
    private List<String> languages = Arrays.asList("English", "Vietnamese", "Spanish", "French");

    @FXML
    public void initialize() {
        // Populate both ComboBoxes with language options
        sourceLanguageComboBox.getItems().addAll(languages);
        targetLanguageComboBox.getItems().addAll(languages);

        // Set default values for ComboBoxes
        sourceLanguageComboBox.setValue("English");
        targetLanguageComboBox.setValue("Vietnamese");

        // Add event handlers to update ComboBox options
        sourceLanguageComboBox.setOnAction(e -> updateTargetLanguageOptions());
        targetLanguageComboBox.setOnAction(e -> updateSourceLanguageOptions());
    }

    @FXML
    public void translateWord() {
        String sourceLanguage = getLanguageCode(sourceLanguageComboBox.getValue());
        String targetLanguage = getLanguageCode(targetLanguageComboBox.getValue());
        String textToTranslate = SourceLanguage.getText();
        if (textToTranslate.isEmpty() || textToTranslate.isBlank()) {
            TranslationLanguage.setText("");
            return;
        }
        try {
            String translation = TranslateManager.translateWord(textToTranslate, sourceLanguage, targetLanguage);
            TranslationLanguage.setText(translation);
        } catch (IOException e) {
            // Handle exception
            e.printStackTrace();
        }
    }

    @FXML
    public void handleVoice() {
        String textToTranslate = SourceLanguage.getText();
        if (textToTranslate.isEmpty() || textToTranslate.isBlank()) {
            return;
        }
        VoiceManager.playVoice(textToTranslate);
    }

    // Helper method to get the language code based on language name
    private String getLanguageCode(String languageName) {
        switch (languageName) {
            case "Vietnamese":
                return "vi";
            case "Spanish":
                return "es";
            case "French":
                return "fr";
            // Add more cases for additional languages
            case "English":
            default:
                return "en";
        }
    }

    // Helper method to update targetLanguageComboBox options
    private void updateTargetLanguageOptions() {
        String selectedSourceLanguage = sourceLanguageComboBox.getValue();
        for (int i = 0; i < targetLanguageComboBox.getItems().size(); i++) {
            if (targetLanguageComboBox.getItems().get(i).equals(selectedSourceLanguage)) {
                targetLanguageComboBox.getItems().remove(i);
            }
        }
    }

    private void updateSourceLanguageOptions() {
        String selectedTargetLanguage = targetLanguageComboBox.getValue();
        for (int i = 0; i < sourceLanguageComboBox.getItems().size(); i++) {
            if (sourceLanguageComboBox.getItems().get(i).equals(selectedTargetLanguage)) {
                sourceLanguageComboBox.getItems().remove(i);
            }
        }
    }
}
