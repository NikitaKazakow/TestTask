package controller;

import db.Statistic;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import lombok.Getter;

import java.io.IOException;

public class ListViewController {
    @FXML
    @Getter
    private VBox vBox;

    @FXML
    private Label date;

    @FXML
    private Label url;

    public ListViewController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ListViewItem.fxml"));
        fxmlLoader.setController(this);
        try
        {
            fxmlLoader.load();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void setInfo(Statistic statistic)
    {
        url.setText(statistic.getUrl());
        date.setText(statistic.getDate().toString());
    }
}
