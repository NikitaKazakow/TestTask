package service.impl;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;
import db.DbConnectionManager;
import db.entity.RecordEntity;
import db.entity.StatisticEntity;
import javafx.concurrent.Task;
import lombok.SneakyThrows;
import service.BaseService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetHistoryService extends BaseService<List<StatisticEntity>> {

    private final JdbcPooledConnectionSource connectionSource;

    @SneakyThrows
    public GetHistoryService() {
        connectionSource = DbConnectionManager.getConnection();
    }

    @Override
    protected Task<List<StatisticEntity>> createTask() {
        return new Task<List<StatisticEntity>>() {
            @Override
            protected List<StatisticEntity> call() {
                List<StatisticEntity> statisticList = new ArrayList<>();
                try {

                    TableUtils.createTableIfNotExists(connectionSource, StatisticEntity.class);
                    TableUtils.createTableIfNotExists(connectionSource, RecordEntity.class);

                    Dao<StatisticEntity, Long> statisticDao =
                            DaoManager.createDao(connectionSource, StatisticEntity.class);
                    statisticList = statisticDao.queryForAll();
                } catch (SQLException ex) {

                    ex.printStackTrace();
                }
                return statisticList;
            }
        };
    }
}
