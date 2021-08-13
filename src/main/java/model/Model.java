package model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;
import db.entity.Record;
import db.entity.Statistic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class Model {

    private final String regex = "[ ,.!?\";:\\[\\]()\n\r\t\\-'/]";

    private final ObservableList<Record> statistic;

    private final GetStatisticService getStatisticService;

    private final SaveDbService saveDbService;

    @SneakyThrows
    public Model() {
        statistic = FXCollections.observableArrayList();

        getStatisticService = new GetStatisticService();

        saveDbService = new SaveDbService();

    }

    public synchronized void getHtmlStatistic(String url) {
        BaseService.url = url;
        getStatisticService.start();
    }

    @Setter
    private abstract static class BaseService extends Service<Void> {
        protected static String url;
    }

    @Setter
    private class GetStatisticService extends BaseService {

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() {
                    statistic.clear();
                    Map<String, Integer> statisticMap = new HashMap<>();
                    try {
                        Document document = Jsoup.connect(url).get();
                        String htmlText = document.body().text();
                        String[] words = htmlText.split(regex);
                        for (String word : words) {
                            if (statisticMap.containsKey(word)) {
                                int count = statisticMap.get(word);
                                statisticMap.replace(word, count, count + 1);
                            } else {
                                statisticMap.put(word, 1);
                            }
                        }
                        statistic.addAll(statisticMap.entrySet().stream().map(stringIntegerEntry ->
                                new Record(stringIntegerEntry.getKey(),
                                        stringIntegerEntry.getValue())).collect(Collectors.toList()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            };
        }

        @Override
        protected void succeeded() {
            saveDbService.start();
        }
    }

    @Setter
    private class SaveDbService extends BaseService {

        private final JdbcPooledConnectionSource connectionSource;

        private final Dao<Statistic, Long> statisticDao;

        @SneakyThrows
        SaveDbService() {
            connectionSource = new JdbcPooledConnectionSource("jdbc:h2:mem:statistic");
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
}
