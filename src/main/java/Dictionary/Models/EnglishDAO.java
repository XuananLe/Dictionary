package Dictionary.Models;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EnglishDAO extends BaseDaoImpl<English, Long> {
    public EnglishDAO(ConnectionSource connectionSource) throws SQLException, SQLException {
        super(connectionSource, English.class);
    }

    public long findIdByWord(String word) throws SQLException {
        return this.queryBuilder().where().eq("Word", word).queryForFirst().getId();
    }

    public boolean updatePronunciation(String word, String pronunciation) throws SQLException {
        try {
            English english = this.queryBuilder().where().eq("Word", word).queryForFirst();
            if (english != null) {
                if (english.getPronunciation().isEmpty())
                    english.setPronunciation(pronunciation);
                else if (!pronunciation.isEmpty())
                    english.setPronunciation(english.getPronunciation() + "\n" + pronunciation);
                this.update(english);
            } else {
                English newEnglish = new English();
                newEnglish.setWord(word);
                newEnglish.setPronunciation(pronunciation);
                this.create(newEnglish);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " insertPronunciation");
            return false;
        }
        return true;
    }

    public boolean updateExample(String word, String example) throws SQLException {
        try {
            English english = this.queryBuilder().where().eq("Word", word).queryForFirst();
            if (english != null) {
                if (english.getExample().isEmpty()) {
                    english.setExample(example);
                } else if (!example.isEmpty())
                    english.setExample(english.getExample() + "\n" + example);
                this.update(english);
            } else {
                English newEnglish = new English();
                newEnglish.setWord(word);
                newEnglish.setExample(example);
                this.create(newEnglish);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " insertExample");
            return false;
        }
        return true;
    }

    public boolean updateSynonym(String word, String synonym) throws SQLException {
        try {
            English english = this.queryBuilder().where().eq("Word", word).queryForFirst();
            if (english != null) {
                if (english.getSynonym().isEmpty()) {
                    english.setSynonym(synonym);
                } else if (!synonym.isEmpty())
                    english.setSynonym(english.getSynonym() + "\n" + synonym);
                this.update(english);
            } else {
                English newEnglish = new English();
                newEnglish.setWord(word);
                newEnglish.setSynonym(synonym);
                this.create(newEnglish);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " insertSynonym");
            return false;
        }
        return true;
    }

    public boolean updateType(String word, String type) throws SQLException {
        try {
            English english = this.queryBuilder().where().eq("Word", word).queryForFirst();
            if (english != null) {
                if (english.getType().isEmpty()) {
                    english.setType(type);
                } else if (!type.isEmpty())
                    english.setType(english.getType() + "\n" + type);
                this.update(english);
            } else {
                English newEnglish = new English();
                newEnglish.setWord(word);
                newEnglish.setType(type);
                this.create(newEnglish);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage() + " insertType");
            return false;
        }
        return true;
    }

    public boolean updateAntonyms(String word, String antonyms) throws SQLException {
        try {
            English english = this.queryBuilder().where().eq("Word", word).queryForFirst();
            if (english != null) {
                if (english.getAntonyms().isEmpty()) {
                    english.setAntonyms(antonyms);
                } else if (!antonyms.isEmpty())
                    english.setAntonyms(english.getAntonyms() + "\n" + antonyms);
                this.update(english);
            } else {
                English newEnglish = new English();
                newEnglish.setWord(word);
                newEnglish.setAntonyms(antonyms);
                this.create(newEnglish);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " insertAntonyms");
            return false;
        }
        return true;
    }

    // co the tra ra null
    public English getWord(String word) throws SQLException {
        return this.queryBuilder().where().eq("Word", word).queryForFirst();
    }

    public English getWord(English word) throws SQLException {
        return this.queryBuilder().where().eq("Word", word.getWord()).queryForFirst();
    }

    public boolean updateWord(English x) throws SQLException {
        if (x.getWord().isEmpty() || x.getMeaning().isEmpty()) {
            return false;
        }
        try {
            English english = this.queryBuilder().where().eq("Word", x.getWord()).queryForFirst();
            if (english != null && !english.getWord().isEmpty()) {
                if (english.getMeaning().isEmpty()) {
                    english.setMeaning(x.getMeaning());
                } else if (!x.getMeaning().isEmpty() && !english.getMeaning().contains(x.getMeaning()) && !Objects.equals(x.getMeaning().trim().strip(), english.getMeaning())) {
                    english.setMeaning(english.getMeaning() + "\n" + x.getMeaning());
                }
                english.setType(x.getType());
                english.setPronunciation(x.getPronunciation());
                if (english.getAntonyms().isEmpty()) {
                    english.setAntonyms(x.getAntonyms());
                } else if (!x.getAntonyms().isEmpty() && !english.getAntonyms().contains(x.getAntonyms()) && !Objects.equals(x.getAntonyms(), english.getAntonyms()))
                    english.setAntonyms(english.getAntonyms() + "\n" + x.getAntonyms());

                if (english.getSynonym().isEmpty()) {
                    english.setSynonym(x.getSynonym());
                } else if (!x.getAntonyms().isEmpty() && !english.getSynonym().contains(x.getSynonym()) && !Objects.equals(x.getSynonym(), english.getSynonym()))
                    english.setSynonym(english.getSynonym() + "\n" + x.getSynonym());

                if (english.getExample().isEmpty()) {
                    english.setExample(x.getExample());
                } else if (!x.getExample().isEmpty() && !english.getExample().contains(x.getExample()) && !Objects.equals(x.getExample(), english.getExample()))
                    english.setExample(english.getExample() + "\n" + x.getExample());
                this.update(english);
            } else {
                this.create(x);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " insertWord");
            return false;
        }
        return true;
    }

    public boolean ifExist(String x) throws SQLException {
        English english = this.queryBuilder().where().eq("Word", x).queryForFirst();
        if (english == null) {
            return true;
        }
        return false;
    }

    public boolean ifExist(English english) throws SQLException {
        English english1 = this.queryBuilder().where().eq("Word", english.getWord()).queryForFirst();
        if (english1 == null) {
            return true;
        }
        return false;
    }

    public boolean deleteWord(String word) throws SQLException {
        try {
            Where<English, Long> english = this.queryBuilder().where().eq("Word", word);
            for (English x : english.query()) {
                this.delete(x);
            }
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " deleteWord " + word);
            return false;
        }
    }

    public boolean deleteWord(English english) throws SQLException {
        try {
            Where<English, Long> english1 = this.queryBuilder().where().eq("Word", english.getWord());
            for (English x : english1.query()) {
                this.delete(x);
            }
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " deleteWord " + english.getWord());
            return false;
        }
    }

    public List<English> findWord(String word) throws SQLException {

        Where<English, Long> english = this.queryBuilder().where().eq("Word", word);
        return new ArrayList<>(english.query());
    }

    public List<English> findWord(English english) {
        try {
            Where<English, Long> english1 = this.queryBuilder().where().eq("Word", english.getWord());
            return new ArrayList<>(english1.query());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<English> containWord(String word) throws SQLException {
        Where<English, Long> english = this.queryBuilder().where().like("Word", word + "%");
        return new ArrayList<>(english.query());
    }

    public List<English> containWord(English english) throws SQLException {
        Where<English, Long> english1 = this.queryBuilder().where().like("Word", english.getWord() + "%");
        return new ArrayList<>(english1.query());
    }

    public void changeType(String Word) throws SQLException {
        var english = this.queryBuilder().where().eq("Word", Word).queryForFirst();
        if (english != null) {
            String type = english.getType();
            if (type.contains("n.")) {
                type = type.replace("n.", "noun");
            }
            if (type.contains("v.")) {
                type = type.replace("v.", "verb");
            }
            if(type.contains("t.")){
                type = type.replace("t.", "transitive");
            }
            if (type.contains("Imp.")) {
                type = type.replace("Imp", "Imperative");
            }
            if (type.contains("a.")) {
                type = type.replace("a.", "adjective");
            }
            if (type.contains("adv.")) {
                type = type.replace("adv.", "adverb");
            }
            if (type.contains("pr.")) {
                type = type.replace("pr.", "pronoun");
            }
            if(type.contains("i.")){
                type = type.replace("i.", "intransitive");
            }
            if(type.contains("pl.")){
                type = type.replace("pl.", "plural");
            }
            if (type.contains("p.")) {
                type = type.replace("p.", "preposition");
            }
            if (type.contains("conj.")) {
                type = type.replace("conj.", "conjunction");
            }
            if (type.contains("interj")) {
                type = type.replace("interj.", "interjection");
            }
            if(type.contains("&")){
                type = type.replace("&", " and ");
            }
            if(type.contains("vb.")){
                type = type.replace("vb.", "verbNoun");
            }
            english.setType(type);
            this.update(english);
        }
    }
    public List<English> getAllWords() throws SQLException {
        return this.queryForAll();
    }

    public String renderDefinition(English english) {
        if (english == null) {
            return "";
        }

        StringBuilder definitionBuilder = new StringBuilder();

        if (!english.getType().isEmpty()) {
            definitionBuilder.append("Part of Speech: ").append(english.getType()).append("\n").append("\n");
        }
        else {
            definitionBuilder.append("Part of Speech: ").append("No part of speech found").append("\n").append("\n");
        }
        if (!english.getMeaning().isEmpty()) {
            definitionBuilder.append("Definition: ").append(english.getMeaning()).append("\n").append("\n");
        }
        else {
            definitionBuilder.append("Definition: ").append("No definition found").append("\n").append("\n");
        }

        if(!english.getPronunciation().isEmpty()){
            definitionBuilder.append("Pronunciation: ").append(english.getPronunciation()).append("\n").append("\n");
        }
        else {
            definitionBuilder.append("Pronunciation: ").append("No pronunciation found").append("\n").append("\n");
        }

        if (!english.getSynonym().isEmpty()) {
            definitionBuilder.append("Synonym: ").append(english.getSynonym()).append("\n").append("\n");
        }else {
            definitionBuilder.append("Synonym: ").append("No synonym found").append("\n").append("\n");
        }
        if (!english.getAntonyms().isEmpty()) {
            definitionBuilder.append("Antonym: ").append(english.getAntonyms()).append("\n").append("\n");
        }else {
            definitionBuilder.append("Antonym: ").append("No antonym found").append("\n").append("\n");
        }
        if (!english.getExample().isEmpty()) {
            definitionBuilder.append("Example: ").append(english.getExample()).append("\n").append("\n");
        } else {
            definitionBuilder.append("Example: ").append("No example found").append("\n").append("\n");
        }
        return definitionBuilder.toString().trim();
    }

   public boolean sortedWord() throws SQLException {
        var x = this.queryBuilder();
        try {
            x.orderBy("Word", true);
        }
        catch (Exception e){
            System.err.println(e.getMessage() + " sortedWord");
            return false;
        }
        return true;
    }

}
