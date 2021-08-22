package model.impl;

import dto.RecordDto;
import dto.StatisticDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import lombok.Getter;
import model.ModelBase;
import service.impl.ClearHistoryService;
import service.impl.StatisticRecordsService;
import service.impl.GetHistoryService;

import java.util.stream.Collectors;

public class HistoryTabModel extends ModelBase {

    @Getter
    private final ObservableList<StatisticDto> historyObservableList;

    @Getter
    private final ObservableList<RecordDto> selectedStatisticRecordsObservableList;

    private final GetHistoryService getHistoryService;
    private final StatisticRecordsService statisticRecordsService;
    private final ClearHistoryService clearHistoryService;

    public HistoryTabModel() {

        super();

        historyObservableList = FXCollections.observableArrayList();
        selectedStatisticRecordsObservableList = FXCollections.observableArrayList();

        getHistoryService = new GetHistoryService();
        statisticRecordsService = new StatisticRecordsService();
        clearHistoryService = new ClearHistoryService();

        getHistoryService.setOnSucceeded(
                e -> {
                    historyObservableList.clear();
                    historyObservableList.addAll(
                            getHistoryService.getValue()
                                    .stream()
                                    .map(
                                            statisticEntity -> modelMapper.map(statisticEntity, StatisticDto.class))
                                    .collect(Collectors.toList()));
                }
        );

        statisticRecordsService.setOnSucceeded(
                e -> {
                    selectedStatisticRecordsObservableList.clear();
                    selectedStatisticRecordsObservableList.addAll(
                            statisticRecordsService.getValue()
                                    .stream()
                                    .map(
                                            recordEntity -> modelMapper.map(recordEntity, RecordDto.class))
                                    .collect(Collectors.toList()));
                }
        );

        clearHistoryService.setOnSucceeded(
                e -> {
                    historyObservableList.clear();
                    historyObservableList.addAll(
                            clearHistoryService.getValue()
                                    .stream()
                                    .map(
                                            statisticEntity -> modelMapper.map(statisticEntity, StatisticDto.class))
                                    .collect(Collectors.toList()));
                    selectedStatisticRecordsObservableList.clear();
                }
        );
    }

    @Override
    public void clear() {
        historyObservableList.clear();
        selectedStatisticRecordsObservableList.clear();
    }

    public void getStatisticHistory() {
        if (getHistoryService.getState() == Worker.State.READY) {
            getHistoryService.start();
        } else {
            getHistoryService.restart();
        }
    }

    public void getRecords(StatisticDto statisticDto) {
        statisticRecordsService.setStatisticDate(statisticDto.getDate());
        if (statisticRecordsService.getState() == Worker.State.READY) {
            statisticRecordsService.start();
        } else {
            statisticRecordsService.restart();

        }
    }

    public void clearHistory() {
        if (clearHistoryService.getState() == Worker.State.READY) {
            clearHistoryService.start();
        } else {
            clearHistoryService.restart();
        }
    }
}
