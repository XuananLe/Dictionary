package Dictionary.Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static Dictionary.Services.EncodingService.base64ToImage;
import static Dictionary.Services.EncodingService.imageToBase64;

public class ImageTranslationService {
    public static final URL SERVER_URL;

    static {
        try {
            // Flask server connection string
            SERVER_URL = new URL("http://127.0.0.1:5000");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendImageBase64ToServer(String ImagePath) throws IOException { // image -> base64 -> server -> base64 -> image


        // Set up connection
        String base64Data = imageToBase64(ImagePath);
        URL ImageServerUrl = new URL(SERVER_URL + "/image");
        HttpURLConnection conn = (HttpURLConnection) ImageServerUrl.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        String jsonData = "{\"image_data\":\"" + base64Data + "\"}";


        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonData.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }


        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            responseLine = response.toString();
            responseLine = responseLine.substring(16, responseLine.length() - 2);
            try {
                base64ToImage(responseLine);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static void sendImageToServer() throws IOException {
        sendImageBase64ToServer("Client.png");
    }

}
