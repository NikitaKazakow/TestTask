package service.impl;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;
import db.DbConnectionManager;
import db.entity.RecordEntity;
import db.entity.StatisticEntity;
import javafx.concurrent.Task;
import lombok.Setter;
import lombok.SneakyThrows;
import service.BaseService;

import java.sql.SQLException;
import java.util.List;

public class SaveDbService extends BaseService<Void> {

    @Setter
    private List<RecordEntity> statistic;

    private final JdbcPooledConnectionSource connectionSource;

    @SneakyThrows
    public SaveDbService() {
        connectionSource = DbConnectionManager.getConnection();
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() {
                try {

                    TableUtils.createTableIfNotExists(connectionSource, StatisticEntity.class);
                    TableUtils.createTableIfNotExists(connectionSource, RecordEntity.class);

                    StatisticEntity dbStatistic = new StatisticEntity();
                    dbStatistic.setUrl(url);

                    Dao<StatisticEntity, Long> statisticDao = DaoManager.createDao(connectionSource, StatisticEntity.class);
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
