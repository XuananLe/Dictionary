package Dictionary;

import Dictionary.Models.*;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class DatabaseConfig {


    // INIT The Database Connection

    private static final String DATABASE_URL = "jdbc:sqlite:test123.db";

    static ConnectionSource connectionSource;

    static {
        try {
            connectionSource = new JdbcConnectionSource(DATABASE_URL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    

    static EnglishDAO englishDAO;

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
        LoadData();
    }
    public static void LoadData() throws SQLException{
        String csvFile = "./englishDictionary.csv";
        List<English> englishList = parseEnglish(csvFile);
        for (English english : englishList) {
            englishDAO.updateWord(english);
        }
    }
    public static List<English> parseEnglish(String csvFile) {
        List<English> englishList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip the header line
                }

                String[] values = line.split(",", -1);
                if (values.length >= 3) {
                    String word = values[0].trim();
                    String pos = values[1].trim();
                    String def = values[2].trim();
                    English english = new English();
                    english.setWord(word);
                    english.setType(pos);
                    english.setMeaning(def);
                    englishList.add(english);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return englishList;
    }
}