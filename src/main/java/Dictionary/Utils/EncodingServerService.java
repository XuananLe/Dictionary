package Dictionary.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class EncodingServerService {
    public static final URL SERVER_URL;

    static {
        try {
            // Flask server connection string
            SERVER_URL = new URL("http://127.0.0.1:5000");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String imageToBase64(String ImagePath) throws IOException {
        byte[] fileContent = Files.readAllBytes(Paths.get(ImagePath));
        return Base64.getEncoder().encodeToString(fileContent);
    }


    // Decode base64 từ server trả về thành ảnh lưu tên result.jpeg
    public static void base64ToImage(String base64Image) throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(base64Image);
        Files.write(Paths.get("result.png"), decodedBytes);
    }

    public static void sendImageBase64ToServer(String ImagePath) throws IOException { // image -> base64 -> server -> base64 -> image

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
            String responseLine = "";
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

    public static void base64ToWav(String base64Wav) throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(base64Wav);
        Files.write(Paths.get("Result.wav"), decodedBytes);
    }

    public static String wavToBase64(String wavPath) throws IOException {
        byte[] fileContent = Files.readAllBytes(Paths.get(wavPath));
        return Base64.getEncoder().encodeToString(fileContent);
    }


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
