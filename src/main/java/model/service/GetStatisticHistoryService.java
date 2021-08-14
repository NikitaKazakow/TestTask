package model.service;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import db.Statistic;
import javafx.concurrent.Task;
import lombok.SneakyThrows;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetStatisticHistoryService extends BaseService<List<Statistic>> {

    private final Dao<Statistic, Long> statisticDao;

    @SneakyThrows
    public GetStatisticHistoryService(String path, String username, String password) {
        statisticDao = DaoManager.createDao(
                new JdbcPooledConnectionSource(path, username, password), Statistic.class);
    }

    @Override
    protected Task<List<Statistic>> createTask() {
        return new Task<List<Statistic>>() {
            @Override
            protected List<Statistic> call() {
                List<Statistic> statisticList = new ArrayList<>();
                try {
                    statisticList = statisticDao.queryForAll();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                return statisticList;
            }
        };
    }
}
