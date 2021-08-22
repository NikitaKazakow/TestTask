package controller.impl.tabs;

import controller.ControllerBase;
import db.entity.RecordEntity;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.impl.StatisticTabModel;
import org.apache.commons.validator.routines.UrlValidator;

import java.net.URL;
import java.util.ResourceBundle;

public class StatisticTabController extends ControllerBase<StatisticTabModel> {

    private UrlValidator urlValidator;

    @FXML
    private TextField urlTextField;

    @FXML
    private Button getStatisticButton;

    @FXML
    private TableView<RecordEntity> table;

    @FXML
    private void getStatistics() {
        model.getHtmlStatistic();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        urlValidator = new UrlValidator(new String[]{"http", "https"});
        model = new StatisticTabModel();

        table.setItems(model.getStatistic());

        table.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("Word"));
        table.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("Count"));

        getStatisticButton.disableProperty().bind(Bindings.createBooleanBinding(
                () -> {
                    String url = urlTextField.getText();
                    if (url == null) {
                        return false;
                    }
                    else {
                        return url.isEmpty() || !urlValidator.isValid(url);
                    }
                }, urlTextField.textProperty()
        ));

        urlTextField.styleProperty().bind(Bindings.createObjectBinding(
                () -> {
                    String url = urlTextField.getText();
                    if (url == null ||url.isEmpty() ) {
                        return null;
                    }
                    else if (!urlValidator.isValid(url)) {
                        return "-fx-text-box-border: #B22222; -fx-focus-color: #B22222;";
                    }
                    else return null;
                }, urlTextField.textProperty()
        ));

        urlTextField.textProperty().bindBidirectional(model.getUrl());
    }

    @Override
    public void clearModelData() {
        model.clear();
    }
}
