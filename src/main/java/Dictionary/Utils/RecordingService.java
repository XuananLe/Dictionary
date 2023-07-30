package Dictionary.Utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class RecordingService {
    private TargetDataLine targetDataLine;
    private static RecordingService instance;
    private boolean isRecording;

    private RecordingService() {
        AudioFormat audioFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                44100,
                16,
                2,
                4,
                44100,
                false
        );

        try {
            targetDataLine = AudioSystem.getTargetDataLine(audioFormat);
            targetDataLine.open(audioFormat);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        isRecording = false;
    }

    public static RecordingService getInstance() {
        if (instance == null) {
            instance = new RecordingService();
        }
        return instance;
    }

    public void startRecording() {
        if (!isRecording && targetDataLine != null) {
            isRecording = true;
            targetDataLine.start();

            new Thread(() -> {
                AudioInputStream audioInputStream = new AudioInputStream(targetDataLine);

                try {
                    File wavFile = new File("Client.wav");
                    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
                    AudioSystem.write(audioInputStream, fileType, wavFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                targetDataLine.stop();
                targetDataLine.close();
                isRecording = false;
            }).start();
        }
    }

    public void stopRecording() {
        isRecording = false;
    }
}
