package co.edu.uniquindio.gestorcontactos.controladores;

import co.edu.uniquindio.gestorcontactos.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import lombok.Setter;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class ContactoController implements Initializable {

    @FXML
    private Button perfilButton;

    @FXML
    private TextField telefonotxt;

    @FXML
    private TextField nombretxt;

    @FXML
    private Button cancelarButton;

    @FXML
    private TextField apellidotxt;

    @FXML
    private TextField emailtxt;

    @FXML
    private DatePicker cumpleaniosDate;

    @FXML
    private ImageView imagenContacto;

    @FXML
    private Button confirmarButton;

    @Setter
    private App app;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void perfilButtonAction(ActionEvent event) {

    }

    @FXML
    void confirmarButtonAction(ActionEvent event) {

    }

    @FXML
    void cancelarButtonAction(ActionEvent event) {

    }
}
