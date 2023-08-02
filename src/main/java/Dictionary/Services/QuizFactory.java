package Dictionary.Services;

import java.sql.SQLException;
import java.util.Random;

import static Dictionary.DatabaseConfig.englishDAO;
import Dictionary.Models.English;
import java.util.List;
public class QuizFactory {
    long seed = System.currentTimeMillis();
    Random random = new Random(seed);

    enum QuestionType {
        ChooseMeaning,
        ChooseWord,
        ListenAndChoose,
        FillBlank,
    }

    private String question;
    private String[] choices = new String[4];
    private int scores;
    private int dbSize = englishDAO.getAllWords().size();
    private List<English> englishList = englishDAO.getAllWords();
    private int typeOfQuestion;
    private String inputAnswer;
    private String trueAnswer;
    private int playTimes;

    public int getPlayTimes() {
        return playTimes;
    }

    public QuizFactory() throws SQLException {
    }

    public void playAgain() {
        if (playTimes < 5) {
            initQuiz();
            playTimes++;
            if (checkAnswer()) {
                increaseScore();
                System.out.println("Correct");
            }
        } else {
            endQuiz();
        }
    }

    public String endQuiz() {
        return "Your score: " + scores;
    }

    public String generateQuestion() {
        switch (QuestionType.values()[typeOfQuestion]) {
            case ChooseMeaning:
                return "What is the meaning of " + question + "?";
            case ChooseWord:
                return "What is the word for " + question + "?";
            case FillBlank:
                return "Fill in the blank: " + question;
            case ListenAndChoose:
                VoiceService.playVoice(getTrueAnswer());
                return "Listen to the audio and choose the correct answer";
            default:
                return "";
        }
    }

    public void initQuiz() {
        // get random type
        int random = (int) (Math.random() * 4);
        setTypeOfQuestion(random);
        switch (QuestionType.values()[getTypeOfQuestion()]) {
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
            default:
                break;
        }
    }

    public boolean checkAnswer() {
        return inputAnswer.equals(trueAnswer);
    }

    public void initChooseMeaningQuiz() {
        for (int i = 0; i < 4; i++) {
            choices[i] = getRandomMeaning();
        }
        // get random from 0 to 3
        int random = (int) (Math.random() * 4);
        setQuestion(getWordFromMeaning(choices[random]));
        setTrueAnswer(choices[random]);
    }

    public void initChooseWordQuiz() {
        for (int i = 0; i < 4; i++) {
            choices[i] = getRandomWord();
        }
        int random = (int) (Math.random() * 4);
        setQuestion(getMeaningFromWord(choices[random]));
        setTrueAnswer(choices[random]);
    }

    public void initFillBlankQuiz() {
        for (int i = 0; i < 4; i++) {
            choices[i] = getRandomWord();
        }
        int random = (int) (Math.random() * 4);
        setQuestion(getMeaningFromWord(choices[random]));
        setTrueAnswer(choices[random]);
        question.replace(choices[random], "______");

    }

    public void initListenQuiz() {
        for (int i = 0; i < 4; i++) {
            choices[i] = getRandomWord();
        }
        int random = (int) (Math.random() * 4);
        setTrueAnswer(choices[random]);
        setQuestion(getMeaningFromWord(choices[random]));
    }

    public void increaseScore() {
        scores++;
    }

    public String getRandomWord() {
        try {
            int random = (int) (Math.random() * dbSize);
            return englishList.get(random).getWord();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    public String getRandomMeaning() {
        try {
            int random = (int) (Math.random() * dbSize);
            return englishList.get(random).getMeaning();
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

    public String getTrueAnswer() {
        return trueAnswer;
    }

    public void setTrueAnswer(String trueAnswer) {
        this.trueAnswer = trueAnswer;
    }

    public int getTypeOfQuestion() {
        return typeOfQuestion;
    }

    public void setTypeOfQuestion(int type) {
        this.typeOfQuestion = type;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String ques) {
        this.question = ques;
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

    public String getInputAnswer() {
        return inputAnswer;
    }

    public void setInputAnswer(String answer) {
        this.inputAnswer = answer;
    }

}
