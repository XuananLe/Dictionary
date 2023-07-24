package Dictionary.Utils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Api {
    public static String translateWord(String textToTranslate, String sourceLanguage, String targetLanguage) {
        try {
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
            reader.close();
            return parseTranslationResponse(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String parseTranslationResponse(String response) {
        return response.substring(4, response.indexOf("\"", 4));
    }
    public static void main(String[] args) {
        System.out.println(translateWord("hello", "en", "vi"));
    }
}
