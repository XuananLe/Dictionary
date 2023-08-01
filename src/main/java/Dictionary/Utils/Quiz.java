package Dictionary.Utils;

import static Dictionary.DatabaseConfig.englishDAO;

import java.util.Scanner;

public class Quiz {
    private VoiceService voiceService = new VoiceService();

    enum QuestionType {
        ChooseMeaning,
        ChooseWord,
        ListenAndChoose,
        ListenAndFillBlank,
        FillBlank,
    }

    private String ques;
    private String[] choices = new String[4];
    private int scores;
    private int type;
    private String answer;
    private int choose;

    public int getChoose() {
        return choose;
    }

    public void setChoose(int choose) {
        this.choose = choose;
    }

    Quiz() {
        this.type = 0;
        this.scores = 0;
        this.ques = "";
        this.answer = "";
    }

    public void quiz() {
        for (int i = 0; i < 5; i++) {
            startQuiz();
        }
        endQuiz();
    }

    public void startQuiz() {
        // get random type
        int random = (int) (Math.random() * 2);
        setType(random);
        initQuiz(type);
        System.out.println(getQuestion(type, ques));
        getInput(answer);
        while (!checkAnswer()) {
            System.out.println("Wrong answer, try again!");
            getInput(answer);
        }
        increaseScore();
        System.out.println("Correct answer!");
    }

    public void endQuiz() {
        System.out.println("Your score: " + scores);
    }

    public void getInput(String input) {
        Scanner scanner = new Scanner(System.in);
        input = scanner.nextLine();
        System.out.println("You entered: " + input);
        setAnswer(input);
        // close scanner
        scanner.close();

    }

    public void initQuiz(int type) {
        switch (QuestionType.values()[type]) {
            case ChooseMeaning:
                initChooseMeaningQuiz();
                break;
            case ChooseWord:
                initChooseWordQuiz();
                break;
            case FillBlank:
                initFillBlankQuiz();
                break;
            case ListenAndChoose:
                initListenQuiz();
                break;
            case ListenAndFillBlank:
                initListenQuiz();
                break;
            default:
                break;
        }
    }

    public boolean checkAnswer() {
        if (type == 0) {
            return answer.equals(getMeaningFromWord(ques));
        } else {
            return answer.equals(getWordFromMeaning(ques));
        }
    }

    // public boolean checkAnswer() {
    //     switch (type) {
    //         case 0:
    //             return answer.equals(getMeaningFromWord(ques));
    //         case 1:
    //             return answer.equals(getWordFromMeaning(ques));
    //         case 2:
    //             return answer.equals(getWordFromMeaning(ques));
    //         case 3:

    //     }
    // }
    public void initChooseMeaningQuiz() {
        for (int i = 0; i < 4; i++) {
            choices[i] = getRandomMeaning();
        }
        // get random from 0 to 3
        int random = (int) (Math.random() * 4);
        setQues(getWordFromMeaning(choices[random]));
    }

    public void initChooseWordQuiz() {
        for (int i = 0; i < 4; i++) {
            choices[i] = getRandomWord();
        }
        int random = (int) (Math.random() * 4);
        setQues(getMeaningFromWord(choices[random]));
    }

    public void initListenQuiz() {
        for (int i = 0; i < 4; i++) {
            choices[i] = getRandomWord();
        }
        int random = (int) (Math.random() * 4);
        setQues(getMeaningFromWord(choices[random]));
        ques.replace(choices[random], "______");
    }

    public void initFillBlankQuiz() {
        for (int i = 0; i < 4; i++) {
            choices[i] = getRandomWord();
        }
        int random = (int) (Math.random() * 4);
        setQues(getWordFromMeaning(choices[random]));
    }

    public void increaseScore() {
        scores++;
    }

    public String getRandomWord() {
        try {
            int size = englishDAO.getAllWords().size();
            int random = (int) (Math.random() * size);
            return englishDAO.getAllWords().get(random).getWord();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    public String getRandomMeaning() {
        try {
            int size = englishDAO.getAllWords().size();
            int random = (int) (Math.random() * size);
            return englishDAO.getAllWords().get(random).getMeaning();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    // get word from meaning
    public static String getWordFromMeaning(String meaning) {
        try {
            return englishDAO.queryBuilder().where().eq("meaning", meaning).queryForFirst().getWord();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    // get meaning from word

    public static String getMeaningFromWord(String word) {
        try {
            return englishDAO.queryBuilder().where().eq("word", word).queryForFirst().getMeaning();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getQues() {
        return ques;
    }

    public void setQues(String ques) {
        this.ques = ques;
    }

    public String[] getChoices() {
        return choices;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }

    public int getScores() {
        return scores;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion(int type, String ques) {
        switch (QuestionType.values()[type]) {
            case ChooseMeaning:
                return "What is the meaning of " + ques + "?";
            case ChooseWord:
                return "What is the word for " + ques + "?";
            case FillBlank:
                return "Fill in the blank: " + ques;
            case ListenAndChoose:
                return "Listen to the audio and choose the correct word";
            case ListenAndFillBlank:
                return "Listen to the audio and fill in the blank";
            default:
                return "";
        }
    }

}
