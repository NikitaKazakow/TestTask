package model;

import controller.ListViewController;
import db.Statistic;
import javafx.scene.control.ListCell;

public class ListViewCell extends ListCell<Statistic> {
    @Override
    protected void updateItem(Statistic item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null) {
            ListViewController listViewController = new ListViewController();
            listViewController.setInfo(item);
            setGraphic(listViewController.getVBox());
        }
    }
}
