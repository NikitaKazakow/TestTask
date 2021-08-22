package model.impl;

import db.entity.RecordEntity;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import lombok.Getter;
import model.ModelBase;
import service.BaseService;
import service.impl.HttpRequestService;
import service.impl.SaveDbService;

public class StatisticTabModel extends ModelBase {

    @Getter
    private final ObservableList<RecordEntity> statistic;

    @Getter
    private final SimpleStringProperty url;

    private final SaveDbService saveDbService;
    private final HttpRequestService httpRequestService;

    public StatisticTabModel() {
        super();

        url = new SimpleStringProperty(this, "url");

        statistic = FXCollections.observableArrayList();

        saveDbService = new SaveDbService();
        httpRequestService = new HttpRequestService();

        httpRequestService.setOnSucceeded(
                e -> {

                    clear();
                    statistic.addAll(httpRequestService.getValue());

                    saveDbService.setStatistic(statistic);
                    if (saveDbService.getState() == Worker.State.READY) {
                        saveDbService.start();
                    } else {
                        saveDbService.restart();
                    }
                }
        );
    }

    public void getHtmlStatistic() {
        BaseService.setUrl(url.getValue());
        if (httpRequestService.getState() == Worker.State.READY) {
            httpRequestService.start();
        } else {
            httpRequestService.restart();
        }
    }

    @Override
    public void clear() {
        statistic.clear();
    }
}
