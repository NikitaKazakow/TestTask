package controller;

import db.Statistic;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import model.ListViewCell;
import model.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class HistoryController implements Initializable {

    private Model model;

    @FXML
    private ListView<Statistic> statisticListView;

    public void getHistory() {
        model.getStatisticHistory();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = Model.getInstance();
        statisticListView.setItems(model.getHistory());
        statisticListView.setCellFactory(
                param -> new ListViewCell()
        );
    }
}
