package controller;

import javafx.fxml.Initializable;
import lombok.Setter;
import model.ModelBase;

public abstract class ControllerBase<T extends ModelBase> implements Initializable {

    @Setter
    protected T model;

    public void clearModelData() {
        model.clear();
    }
}
