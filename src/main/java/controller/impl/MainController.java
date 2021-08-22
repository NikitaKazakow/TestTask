package controller.impl;

import controller.ControllerBase;
import controller.impl.tabs.HistoryTabController;
import controller.impl.tabs.StatisticTabController;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import model.ModelBase;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends ControllerBase<ModelBase> {

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab historyTab;

    @FXML
    private Tab statisticTab;

    @FXML
    private HistoryTabController historyTabPageController;

    @FXML
    private StatisticTabController getStatisticTabPageController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabPane.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends Tab> observable,
                 Tab oldValue, Tab newValue) -> {
                    if (newValue == historyTab) {
                        historyTabPageController.clearModelData();
                        historyTabPageController.getHistory();
                    }
                    if (newValue == statisticTab) {
                        getStatisticTabPageController.clearModelData();
                    }
                }
        );
    }

    @Override
    public void clearModelData() {
        model.clear();
    }
}
