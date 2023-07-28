package Dictionary.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class TranslateService {
    public static String translateWord(String textToTranslate, String sourceLanguage, String targetLanguage) throws IOException {
        String encodedText = URLEncoder.encode(textToTranslate, StandardCharsets.UTF_8);

        String apiUrl = "https://translate.googleapis.com/translate_a/single?client=gtx&sl="
                + sourceLanguage + "&tl=" + targetLanguage + "&dt=t&text=" + encodedText + "&op=translate";

        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        return parseTranslationResponse(response.toString());
    }


    public static String parseTranslationResponse(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);

            JSONArray translationArray = jsonArray.getJSONArray(0);

            StringBuilder translatedTextBuilder = new StringBuilder();
            for (int i = 0; i < translationArray.length(); i++) {
                String translationSegment = translationArray.getJSONArray(i).getString(0);
                translatedTextBuilder.append(translationSegment);
            }

            return translatedTextBuilder.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "Error: Unable to parse the translation response.";
        }
    }


    public static void main(String[] args) throws IOException {
        System.out.println(translateWord("hello i love you very much ", "en", "vi"));
    }
}
