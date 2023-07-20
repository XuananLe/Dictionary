package Dictionary.Utils;

public class VoiceManager {
    public static String getVoice(String word) {
        return "https://dict.youdao.com/dictvoice?audio=" + word + "&type=2";
    }

    public static void playVoice(String word) {
        // remote url
        String url = getVoice(word);
        // play
        try {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
        } catch (java.io.IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        playVoice("hello");
    }
}
