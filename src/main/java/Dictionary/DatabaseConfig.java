package Dictionary;
import Dictionary.Models.*;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DatabaseConfig {


    // INIT The Database Connection

    private static final String DATABASE_URL = "jdbc:sqlite:engData.db";

    public static ConnectionSource connectionSource;

    static {
        try {
            connectionSource = new JdbcConnectionSource(DATABASE_URL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    

    public static EnglishDAO englishDAO;

    static {
        try {
            englishDAO = new EnglishDAO(connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    static void init(){
//        try {
//            Class.forName("org.sqlite.JDBC");
//            TableUtils.createTableIfNotExists(connectionSource, English.class);
//            English english = new English("Hello", "Xin chào", "Xin chao", "Xin chào", "Xin chao cai dcm", "Xin chào cai đcm", "Xin chao cai đcm");
//            englishDAO.updateWord(english);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        } finally {
//            if (connectionSource != null) {
//                try {
//                    connectionSource.close();
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
//    }
    public static void main(String[] args) throws SQLException {
        TableUtils.createTableIfNotExists(connectionSource, English.class);
//        English x = new English();
//        x.setWord("A");
//        x.setType("B");
//        x.setMeaning("C");
//        x.setExample("D");
//        englishDAO.updateWord(x);
//           LoadData();
            NormalizeType();
    }


    public static void NormalizeType() throws SQLException {
        var EnglishWords = englishDAO.getAllWords();
        for(var x : EnglishWords){
            englishDAO.changeType(x.getWord());
        }
    }

    public static void LoadData() throws SQLException{
        String csvFile = "/home/xuananle/Documents/Dictionary/src/main/java/Dictionary/final_db.csv";
        List<English> englishList = parseEnglish(csvFile);
        for (English english : englishList) {
            englishDAO.updateWord(english);
        }
    }

    public static String ProccessString(String ColumnText){
        try {
            String trimmedText = ColumnText.substring(1, ColumnText.length() - 1);
            String[] sentencesArray = trimmedText.split("', '");
            String result = "";
            for (int i = 0; i < sentencesArray.length; i++) {
                result += sentencesArray[i].trim();
            }
            return result;
        } catch (Exception e) {
            return ColumnText;
        }
    }


    public static List<English> parseEnglish(String csvFile) {
        List<English> englishList = new ArrayList<>();

        try (Reader reader = new FileReader(csvFile);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
            for (CSVRecord csvRecord : csvParser) {
                try {
                    String word = csvRecord.get(0);
                    word = word.substring(0, 1).toUpperCase() + word.substring(1);
                    String type = csvRecord.get(1);
                    String Meaning = ProccessString(csvRecord.get(2));
                    String Pronunciation = csvRecord.get(3);
                    String Example = ProccessString(csvRecord.get(4));
                    English english = new English();
                    english.setWord(word);
                    english.setType(type);
                    english.setMeaning(Meaning);
                    english.setPronunciation(Pronunciation);
                    english.setExample(Example);
                    englishList.add(english);
                } catch (Exception e) {
                    System.out.println(e.getMessage() + " " + csvRecord.get(0));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return englishList;
    }
}