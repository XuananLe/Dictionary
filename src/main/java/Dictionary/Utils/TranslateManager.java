package Dictionary.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class TranslateManager {
    public static String translateWord(String textToTranslate, String sourceLanguage, String targetLanguage) throws IOException {
        String encodedText = URLEncoder.encode(textToTranslate, "UTF-8");

            String apiUrl = "https://translate.googleapis.com/translate_a/single?client=gtx&sl="
                    + sourceLanguage + "&tl=" + targetLanguage + "&dt=t&q=" + encodedText;

            URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        return parseTranslationResponse(response.toString());
    }
    public static String parseTranslationResponse(String response) {
        return response.substring(4, response.indexOf("\"", 4));
    }

    public static void main(String[] args) throws IOException {
        System.out.println(translateWord("hello i love you very much ", "en", "vi"));
    }
}
