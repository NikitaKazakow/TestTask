package model;

import db.Record;
import db.Statistic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import lombok.Getter;
import model.service.BaseService;
import model.service.GetStatisticHistoryService;
import model.service.GetStatisticService;
import model.service.SaveDbService;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Getter
public class Model {

    private static volatile Model instance;

    private final ObservableList<Record> statistic;

    private final ObservableList<Statistic> history;

    private SaveDbService saveDbService;
    private GetStatisticService getStatisticService;
    private GetStatisticHistoryService historyService;

    private Model() {

        statistic = FXCollections.observableArrayList();
        history = FXCollections.observableArrayList();

        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/config.properties")) {
            properties.load(fileInputStream);

            String dbPath = properties.getProperty("db.path");
            String dbUsername = properties.getProperty("db.username");
            String dbPassword = properties.getProperty("db.password");

            historyService = new GetStatisticHistoryService(dbPath, dbUsername, dbPassword);
            saveDbService = new SaveDbService(dbPath, dbUsername, dbPassword);
            getStatisticService = new GetStatisticService();

            getStatisticService.setOnSucceeded(
                    e -> {

                        statistic.clear();
                        statistic.addAll(getStatisticService.getValue());

                        saveDbService.setStatistic(statistic);
                        if (saveDbService.getState() == Worker.State.READY) {
                            saveDbService.start();
                        } else {
                            saveDbService.restart();
                        }
                    }
            );

            historyService.setOnSucceeded(
                    e -> {
                        history.clear();
                        history.addAll(historyService.getValue());
                    }
            );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Model getInstance() {
        Model model = instance;
        if (model != null) {
            return model;
        }
        synchronized (Model.class) {
            if (instance == null) {
                instance = new Model();
            }
            return instance;
        }
    }

    public void getHtmlStatistic(String url) {
        BaseService.setUrl(url);
        if (getStatisticService.getState() == Worker.State.READY) {
            getStatisticService.start();
        } else {
            getStatisticService.restart();

        }
    }

    public void getStatisticHistory() {
        if (historyService.getState() == Worker.State.READY) {
            historyService.start();
        } else {
            historyService.restart();
        }
    }
}
