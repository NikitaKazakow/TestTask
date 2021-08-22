package db;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import dto.DatabaseCredentials;
import lombok.Setter;

import java.sql.SQLException;

public class DbConnectionManager {

    @Setter
    private static DatabaseCredentials credentials;

    private static JdbcPooledConnectionSource jdbcPooledConnectionSource;

    public static JdbcPooledConnectionSource getConnection() {
        if (jdbcPooledConnectionSource == null) {
            try {
                jdbcPooledConnectionSource = new JdbcPooledConnectionSource(
                  credentials.getUrl(),
                  credentials.getUser(),
                  credentials.getPassword()
                );
                return jdbcPooledConnectionSource;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        else return jdbcPooledConnectionSource;
    }

}
