package Dictionary.Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class VoiceManager {
    public static String getVoice(String word) {
        return "https://dict.youdao.com/dictvoice?audio=" + word + "&type=2";
    }

    public static void playVoice(String word) {
        try {
            String audioUrl = getVoice(word);
            String command = "curl \"" + audioUrl + "\" > a.wav; mpg123 a.wav";

            ProcessBuilder processBuilder = new ProcessBuilder("/bin/sh", "-c", command);
            Process process = processBuilder.start();

            int exitCode = process.waitFor();
            System.out.println("Command execution finished with exit code: " + exitCode);

            // Delete the a.wav file
            Path filePath = Paths.get("a.wav");
            Files.delete(filePath);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        playVoice("hello");
    }
}