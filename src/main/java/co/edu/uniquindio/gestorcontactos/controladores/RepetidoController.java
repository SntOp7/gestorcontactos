package co.edu.uniquindio.gestorcontactos.controladores;

import co.edu.uniquindio.gestorcontactos.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

public class RepetidoController extends Controller {

    @FXML
    private Button cancelarButton;

    @FXML
    private Button reemplazarButton;

    @Setter
    private App app;

    @FXML
    void reemplazarAction(ActionEvent event) {
        reemplazar = true;
        cerrarView();
    }

    @FXML
    void cancelarAction(ActionEvent event) {
        reemplazar = false;
        cerrarView();
    }

    private void cerrarView() {
        Stage stage = (Stage) cancelarButton.getScene().getWindow();
        stage.close();
    }
}
