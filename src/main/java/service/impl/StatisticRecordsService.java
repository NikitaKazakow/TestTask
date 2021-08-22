package service.impl;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.TableUtils;
import db.DbConnectionManager;
import db.entity.RecordEntity;
import db.entity.StatisticEntity;
import javafx.concurrent.Task;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import service.BaseService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StatisticRecordsService extends BaseService<List<RecordEntity>> {

    private final JdbcPooledConnectionSource connectionSource;

    @Getter
    @Setter
    private Date statisticDate;

    @SneakyThrows
    public StatisticRecordsService() {
        connectionSource = DbConnectionManager.getConnection();
    }

    @Override
    protected Task<List<RecordEntity>> createTask() {
        return new Task<List<RecordEntity>>() {
            @Override
            protected List<RecordEntity> call() {
                List<RecordEntity> recordEntityList = new ArrayList<>();
                try {

                    TableUtils.createTableIfNotExists(connectionSource, StatisticEntity.class);
                    TableUtils.createTableIfNotExists(connectionSource, RecordEntity.class);

                    Dao<StatisticEntity, Long> statisticDao = DaoManager.createDao(connectionSource, StatisticEntity.class);
                    QueryBuilder<StatisticEntity, Long> statisticQueryBuilder = statisticDao.queryBuilder();
                    statisticQueryBuilder.where().eq("date", statisticDate);
                    StatisticEntity statisticEntity = statisticQueryBuilder.query().get(0);
                    recordEntityList = new ArrayList<>(statisticEntity.getRecords());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return recordEntityList;
            }
        };
    }
}
