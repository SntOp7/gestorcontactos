package co.edu.uniquindio.gestorcontactos.controladores;

import co.edu.uniquindio.gestorcontactos.App;
import co.edu.uniquindio.gestorcontactos.modelo.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import lombok.Setter;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ContactoController extends Controller implements Initializable {

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
        cargarData();
        cargarImagen();
    }

    private void cargarData() {
        Usuario usuario = app.getPrincipalController().getUsuarioSelected();
        if (usuario != null) {
            nombretxt.setText(usuario.getNombre());
            apellidotxt.setText(usuario.getApellido());
            telefonotxt.setText(usuario.getTelefono());
            cumpleaniosDate.setValue(usuario.getFechaCumpleanios());
            emailtxt.setText(usuario.getCorreo());
        }
    }

    @FXML
    void perfilButtonAction(ActionEvent event) {

    }

    @FXML
    void confirmarButtonAction(ActionEvent event) {
        Usuario usuario = app.getPrincipalController().getUsuarioSelected();
        if (usuario != null) {
            editarUsuario(usuario);
        } else {
            agregarUsuario();
        }
    }

    @FXML
    void cancelarButtonAction(ActionEvent event) {
        Stage stage = (Stage) cancelarButton.getScene().getWindow();
        stage.close();
    }

    private void editarUsuario(Usuario usuario) {
        String nombre = nombretxt.getText();
        String apellido = apellidotxt.getText();
        String telefono = telefonotxt.getText();
        LocalDate fechaCumpleanios = cumpleaniosDate.getValue();
        String correo = emailtxt.getText();
        Usuario aux = new Usuario(nombre, apellido, telefono, fechaCumpleanios, correo);
        try {
            app.gestor.editarUsuario(aux);
            super.mostrarAlerta("Se ha editado el contacto", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            super.mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void agregarUsuario() {
            String nombre = nombretxt.getText();
            String apellido = apellidotxt.getText();
            String telefono = telefonotxt.getText();
            LocalDate fechaCumpleanios = cumpleaniosDate.getValue();
            String correo = emailtxt.getText();
        try {
            app.gestor.crearContacto(nombre, apellido, telefono, fechaCumpleanios, correo);
            super.mostrarAlerta("Se ha agregado el contacto.", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            super.mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    private void cargarImagen() {
        try {
            URL imageUrl = getClass().getResource("/imagenes/contacto-3d.png");
            if (imageUrl == null) {
                super.mostrarAlerta("No se encontró la imagen", Alert.AlertType.ERROR);
            }
    
            Image img = new Image(imageUrl.toExternalForm());
            imagenContacto.setImage(img);
        } catch (Exception e) {
            super.mostrarAlerta("No se pudo cargar la imagen", Alert.AlertType.ERROR);
        }
    }
    


    
    

}


    
    


