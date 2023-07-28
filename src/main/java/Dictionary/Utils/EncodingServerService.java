package Dictionary.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

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
        Files.write(Paths.get("result.jpeg"), decodedBytes);
    }

    public static void sendBase64ToServer(String ImagePath) throws IOException {

        String base64Data = imageToBase64(ImagePath);
        HttpURLConnection conn = (HttpURLConnection) SERVER_URL.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        // Json is the same as the one in the server
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
            responseLine = responseLine.substring(15, responseLine.length() - 2);
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

    public static void main(String[] args) throws IOException {
        sendBase64ToServer("/home/xuananle/Pictures/Pictures/Screenshots/Screenshot from 2023-07-24 10-46-36.png");
    }
}
