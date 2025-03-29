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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Setter;

import javafx.event.ActionEvent;

import java.io.File;
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
        cargarImagen("/imagenes/contacto-3d.png", imagenContacto);
    }

    private void cargarData() {
        Usuario usuario = usuarioSelected;
        nombretxt.setText(usuario.getNombre());
        apellidotxt.setText(usuario.getApellido());
        telefonotxt.setText(usuario.getTelefono());
        cumpleaniosDate.setValue(usuario.getFechaCumpleanios());
        emailtxt.setText(usuario.getCorreo());
    }

    @FXML
    void perfilButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen de perfil");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        File archivoSeleccionado = fileChooser.showOpenDialog(null);
        if (archivoSeleccionado != null) {
            try {
                Image imagenSeleccionada = new Image(archivoSeleccionado.toURI().toString());
                imagenContacto.setImage(imagenSeleccionada);
            } catch (Exception e) {
                mostrarAlerta("No se pudo cargar la imagen seleccionada", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void confirmarButtonAction(ActionEvent event) {
        Usuario usuario = usuarioSelected;
        if (usuario != null) {
            editarUsuario(usuario);
            cerrarView();
        } else {
            agregarUsuario();
            cerrarView();
        }
    }

    @FXML
    void cancelarButtonAction(ActionEvent event) {
        cerrarView();
    }

    private void cerrarView() {
        Stage stage = (Stage) cancelarButton.getScene().getWindow();
        stage.close();
    }

    private void editarUsuario(Usuario usuarioOriginal) {
        cargarData();
        String nombre = nombretxt.getText();
        String apellido = apellidotxt.getText();
        String telefono = telefonotxt.getText();
        LocalDate fechaCumpleanios = cumpleaniosDate.getValue();
        String correo = emailtxt.getText();
        String rutaImagenPerfil = imagenContacto.getImage().toString();


        Usuario usuarioEditado = new Usuario(nombre, apellido, telefono, fechaCumpleanios, correo, rutaImagenPerfil);

        try {
            gestor.editarUsuario(usuarioEditado);
            super.mostrarAlerta("Se ha editado el contacto.", Alert.AlertType.INFORMATION);
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
            String rutaImagenPerfil = imagenContacto.getImage().toString();
        try {
            gestor.crearContacto(nombre, apellido, telefono, fechaCumpleanios, correo, rutaImagenPerfil);
            super.mostrarAlerta("Se ha agregado el contacto.", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            super.mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}


    
    


