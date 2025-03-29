package co.edu.uniquindio.gestorcontactos.controladores;

import co.edu.uniquindio.gestorcontactos.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

public class RepetidoController extends Controller implements Initializable {

    @FXML
    private Button cancelarButton;

    @FXML
    private Button reemplazarButton;

    @Setter
    private App app;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void reemplazarAction(ActionEvent event) {

    }

    @FXML
    void cancelarAction(ActionEvent event) {

    }
}
