package service.impl;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;
import db.DbConnectionManager;
import db.entity.RecordEntity;
import db.entity.StatisticEntity;
import javafx.concurrent.Task;
import service.BaseService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClearHistoryService extends BaseService<List<StatisticEntity>> {

    private final JdbcPooledConnectionSource connectionSource;

    public ClearHistoryService() {
        connectionSource = DbConnectionManager.getConnection();
    }

    @Override
    protected Task<List<StatisticEntity>> createTask() {
        return new Task<List<StatisticEntity>>() {
            @Override
            protected List<StatisticEntity> call() {
                List<StatisticEntity> statisticEntities = new ArrayList<>();
                try {
                    TableUtils.createTableIfNotExists(connectionSource, StatisticEntity.class);
                    TableUtils.createTableIfNotExists(connectionSource, RecordEntity.class);

                    Dao<StatisticEntity, Long> statisticDao =
                            DaoManager.createDao(connectionSource, StatisticEntity.class);

                    statisticEntities = statisticDao.queryForAll();
                    statisticDao.delete(statisticEntities);

                    statisticEntities = statisticDao.queryForAll();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return statisticEntities;
            }
        };
    }
}
