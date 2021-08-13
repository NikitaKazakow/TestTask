import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
public class Controller implements Initializable {

    Model model;

    @FXML
    private TextField url;

    @FXML
    private TableView<Model.StatisticModel> table;

    @FXML
    private void getStatistics() {
        model.getHtmlStatistic(url.getText());
    }

    public void initialize(URL location, ResourceBundle resources) {
        model = new Model();

        table.setItems(model.getStatistic());

        table.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("Word"));
        table.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("Count"));
    }
}
