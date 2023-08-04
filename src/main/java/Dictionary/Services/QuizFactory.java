package Dictionary.Services;

import java.sql.SQLException;
import java.util.Random;

import static Dictionary.App.dbSize;
import static Dictionary.App.englishList;
import static Dictionary.DatabaseConfig.englishDAO;

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
    private int typeOfQuestion;
    private String inputAnswer;
    private String trueAnswer;
    private int playTimes;

    public int getPlayTimes() {
        return playTimes;
    }

    public QuizFactory() throws SQLException {
    }

    public void increasePlayTimes() {
        playTimes++;
    }

    public String endQuiz() {
        return "Your score: " + scores;
    }

    public String correctAnswer() {

        return "Correct answer: " + trueAnswer;
    }

    public String generateQuestion() {
        switch (QuestionType.values()[typeOfQuestion]) {
            case ChooseMeaning:
                return "What is the meaning of: " + question + "?";
            case ChooseWord:
                return "What is the word for: " + question + "?";
            case FillBlank:
                return "Fill in the blank: " + question;
            case ListenAndChoose:
                VoiceService.playVoice(getQuestion());
                return "Listen to the audio and choose the correct answer";
            default:
                return "";
        }
    }

    public void initQuiz() {
        // get random type
        int random = (int) (Math.random() * 3);
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
        if (QuestionType.values()[getTypeOfQuestion()] == QuestionType.FillBlank) {
            return StringUtils.normalizeStringForSearch(inputAnswer).equals(trueAnswer);
        }
        return inputAnswer.equals(trueAnswer);
    }

    public boolean validChoice(String choice) {
        if (choice == null || choice.trim().isEmpty()) {
            return false;
        }

        String lowerCaseChoice = choice.trim().toLowerCase();

        if (lowerCaseChoice.startsWith("of") || lowerCaseChoice.startsWith("alt") || lowerCaseChoice.startsWith(" ")) {
            return false;
        }

        return choice.length() < 150;
    }

    public void initChooseMeaningQuiz() {
        for (int i = 0; i < 4; i++) {
            String tmp = getRandomWord();
            while (validChoice(tmp)) {
                tmp = getRandomWord();
            }
            choices[i] = tmp;

        }
        int random = (int) (Math.random() * 4);
        setQuestion(getWordFromMeaning(choices[random]));
        setTrueAnswer(choices[random]);
    }

    public void initChooseWordQuiz() {
        for (int i = 0; i < 4; i++) {
            String tmp = getRandomWord();
            while (!validChoice(tmp) || !validChoice(getMeaningFromWord(tmp))) {
                tmp = getRandomWord();
            }
            choices[i] = tmp;
        }
        int random = (int) (Math.random() * 4);
        setQuestion(StringUtils.normalizeStringForSearch(getMeaningFromWord(choices[random])));
        setTrueAnswer(choices[random]);
    }

    public void initFillBlankQuiz() {
        for (int i = 0; i < 4; i++) {
            String tmp = getRandomWord();
            while (!validChoice(tmp) || !validChoice(getMeaningFromWord(tmp))) {
                tmp = getRandomWord();
            }
            choices[i] = tmp;
        }
        int random = (int) (Math.random() * 4);
        setQuestion(getMeaningFromWord(choices[random]));
        setTrueAnswer(choices[random]);
        String tmp = StringUtils.normalizeStringForSearch(choices[random]);
        question.replace(tmp, "______");

    }

    public void initListenQuiz() {
        for (int i = 0; i < 4; i++) {
            String tmp = getRandomMeaning();
            while (!validChoice(tmp) || !validChoice(getWordFromMeaning(tmp))) {
                tmp = getRandomMeaning();
            }
            choices[i] = tmp;
        }
        int random = (int) (Math.random() * 4);
        setTrueAnswer(choices[random]);
        setQuestion(getWordFromMeaning(choices[random]));
    }

    public void increaseScore() {
        scores++;
    }

    public String getRandomWord() {
        try {
            int random = (int) (Math.random() * dbSize);
            var result = englishList.get(random).getWord();
            while (result == null){
                random = (int) (Math.random() * dbSize);
                result = englishList.get(random).getWord();
            }
            return result;
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
            return "Reuben";
        }
    }

    // get meaning from word
    public static String getMeaningFromWord(String word) {
        try {
            return englishDAO.queryBuilder().where().eq("word", word).queryForFirst().getMeaning();
        } catch (Exception e) {
            return "The world is gonna burn";
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
