package controller;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Tab historyTab;

    @FXML
    private TabPane tabPane;

    @FXML
    private HistoryController historyTabPageController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabPane.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends Tab> observable,
                 Tab oldValue, Tab newValue) -> {
                    if (newValue == historyTab) {
                        historyTabPageController.getHistory();
                    }
                }
        );
    }
}
