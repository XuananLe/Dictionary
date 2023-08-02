package Dictionary.Services;

import java.sql.SQLException;

import static Dictionary.DatabaseConfig.englishDAO;

public class QuizFactory {

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
    private int typeOfQuestion;
    private String inputAnswer;
    private String trueAnswer;

    QuizFactory() throws SQLException {
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
        setTypeOfQuestion(random);
        initQuiz(typeOfQuestion);
        System.out.println(generateQuestion());
        getInput(inputAnswer);
        while (!checkAnswer()) {
            System.out.println("Wrong answer, try again!");
            getInput(inputAnswer);
        }
        increaseScore();
        System.out.println("Correct answer!");
    }

    public void endQuiz() {
        System.out.println("Your score: " + scores);
    }

    public void getInput(String input) {
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
            choices[i] = getRandomMeaning();
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
            return englishDAO.getAllWords().get(random).getWord();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    public String getRandomMeaning() {
        try {
            int random = (int) (Math.random() * dbSize);
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

    public int getDbSize() {
        return dbSize;
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
            // case ListenAndFillBlank:
            // return "Listen to the audio and fill in the blank";
            default:
                return "";
        }
    }
}
