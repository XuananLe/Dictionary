package Dictionary;

import Dictionary.Models.EnglishDAO;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;


public class DatabaseConfig {

    
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

}