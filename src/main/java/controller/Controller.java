package controller;

import db.entity.Record;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Model;
import org.apache.commons.validator.routines.UrlValidator;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private Model model;

    private UrlValidator urlValidator;

    @FXML
    private TextField urlTextField;

    @FXML
    private Button getStatisticButton;

    @FXML
    private TableView<Record> table;

    @FXML
    private void getStatistics() {
        model.getHtmlStatistic(urlTextField.getText());
    }

    public void initialize(URL location, ResourceBundle resources) {
        urlValidator = new UrlValidator(new String[]{"http", "https"});
        model = new Model();

        table.setItems(model.getStatistic());

        table.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("Word"));
        table.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("Count"));

        getStatisticButton.disableProperty().bind(Bindings.createBooleanBinding(
                () -> {
                    String url = urlTextField.getText();
                    return url.isEmpty() || !urlValidator.isValid(url);
                }, urlTextField.textProperty()
        ));

        urlTextField.styleProperty().bind(Bindings.createObjectBinding(
                () -> {
                    String url = urlTextField.getText();
                    if (url.isEmpty() || !urlValidator.isValid(url)) {
                        return "-fx-text-box-border: #B22222; -fx-focus-color: #B22222;";
                    }
                    else return null;
                }, urlTextField.textProperty()
        ));
    }
}
