package Dictionary.Utils;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class VoiceManager {
    public static String getVoice(String word) {
        return "https://dict.youdao.com/dictvoice?audio=" + word + "&type=2";
    }

    public static void downloadUrl(String word) {
        try {
            String audioUrl = getVoice(word);
            URL url = new URL(audioUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (InputStream inputStream = conn.getInputStream();
                        FileOutputStream outputStream = new FileOutputStream("a.wav")) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }
            } else {
                System.err.println("Failed to download audio. Response Code: " + responseCode);
            }

            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void playVoice(String word) {
        downloadUrl(word);
        String audioFilePath = "a.wav";
        try {
            FileInputStream fis = new FileInputStream(audioFilePath);
            AdvancedPlayer player = new AdvancedPlayer(fis, FactoryRegistry.systemRegistry().createAudioDevice());
            player.play();
        } catch (IOException | JavaLayerException e) {
            e.printStackTrace();
        } finally {
            // Delete the file after playing the audio
            File fileToDelete = new File(audioFilePath);
            if (fileToDelete.exists()) {
                if (fileToDelete.delete()) {
                    System.out.println("File deleted successfully.");
                } else {
                    System.err.println("Failed to delete file.");
                }
            }
        }
    }