package model.service;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;
import db.Record;
import db.Statistic;
import javafx.concurrent.Task;
import lombok.Setter;
import lombok.SneakyThrows;

import java.sql.SQLException;
import java.util.List;

public class SaveDbService extends BaseService<Void> {

    @Setter
    private List<Record> statistic;

    private final JdbcPooledConnectionSource connectionSource;

    private final Dao<Statistic, Long> statisticDao;

    @SneakyThrows
    public SaveDbService(String path, String username, String password) {
        connectionSource = new JdbcPooledConnectionSource(path, username, password);
        statisticDao = DaoManager.createDao(connectionSource, Statistic.class);
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() {
                try {

                    TableUtils.createTableIfNotExists(connectionSource, Statistic.class);
                    TableUtils.createTableIfNotExists(connectionSource, Record.class);

                    Statistic dbStatistic = new Statistic();
                    dbStatistic.setUrl(url);

                    statisticDao.create(dbStatistic);
                    statisticDao.refresh(dbStatistic);

                    dbStatistic.getRecords().addAll(statistic);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                return null;
            }
        };
    }
}
