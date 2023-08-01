package Dictionary.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static Dictionary.Utils.EncodingService.SERVER_URL;
import static Dictionary.Utils.EncodingService.wavToBase64;

public class WavTranslationService {
    public static List<String> sendWavBase64ToServer(String wavPath) throws IOException {
        List<String> result = new ArrayList<>();
        String base64Data = wavToBase64(wavPath);

        String jsonData = "{\"wav_data\":\"" + base64Data + "\"}";

        // Send the POST request to the server
        URL imageServerUrl = new URL(SERVER_URL + "/wav");
        HttpURLConnection conn = (HttpURLConnection) imageServerUrl.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonData.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        } catch (IOException e) {
            e.printStackTrace();
            return result;
        }

        StringBuilder responseBuilder = new StringBuilder();
        try (InputStream is = conn.getInputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return result;
        }

        try {
            JSONObject jsonResponse = new JSONObject(responseBuilder.toString());
            String originalText = jsonResponse.optString("original_text", "");
            String translatedText = jsonResponse.optString("translated_text", "");
            result.add(originalText);
            result.add(translatedText);
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<String> sendWavToServer() throws IOException {
        return sendWavBase64ToServer("Client.wav");
    }
}
