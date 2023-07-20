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
                if (english.getType().isEmpty()) {
                    english.setType(x.getType());
                } else if (!x.getType().isEmpty() && !english.getType().contains(x.getType()) && !Objects.equals(x.getType(), english.getType()))
                    english.setType(english.getType() + "\n" + x.getType());

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
}
