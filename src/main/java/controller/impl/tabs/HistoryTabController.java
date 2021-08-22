package controller.impl.tabs;

import controller.ControllerBase;
import controller.impl.StatisticListViewCell;
import dto.RecordDto;
import dto.StatisticDto;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.impl.HistoryTabModel;

import java.net.URL;
import java.util.ResourceBundle;

public class HistoryTabController extends ControllerBase<HistoryTabModel> {

    @FXML
    private TableView<RecordDto> table;

    @FXML
    private ListView<StatisticDto> statisticListView;

    public void getHistory() {
        model.getHistoryObservableList().clear();
        model.getStatisticHistory();
    }

    @FXML
    private void clearHistory() {
        model.clearHistory();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        model = new HistoryTabModel();

        statisticListView.setItems(model.getHistoryObservableList());
        statisticListView.setCellFactory(historyListView -> new StatisticListViewCell());

        statisticListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> model.getRecords(newValue));

        table.setItems(model.getSelectedStatisticRecordsObservableList());
        table.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("Word"));
        table.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("Count"));
    }
}
