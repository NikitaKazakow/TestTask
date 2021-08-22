package controller.impl;

import dto.StatisticDto;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class StatisticListViewCell extends ListCell<StatisticDto> {

    private FXMLLoader fxmlLoader;

    @FXML
    private Label date;

    @FXML
    private Label url;

    @FXML
    private VBox vBox;

    @Override
    protected void updateItem(StatisticDto statistic, boolean empty) {
        super.updateItem(statistic, empty);
        if (empty || statistic == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(getClass().getResource("/view/StatisticListViewItem.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            date.setText(statistic.getDate().toString());
            url.setText(statistic.getUrl());

            setText(null);
            setGraphic(vBox);
        }
    }
}
