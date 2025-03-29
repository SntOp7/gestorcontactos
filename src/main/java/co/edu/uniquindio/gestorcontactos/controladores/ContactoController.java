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



/**
 * Controlador para la vista de gestión de contactos.
 * Permite agregar, editar y visualizar información de contactos.
 *
 * Implementa {@link Initializable} para inicializar la vista con datos del contacto seleccionado.
 */
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



    /**
     * Inicializa la vista del controlador, cargando datos si existe un usuario seleccionado.
     *
     * @param url URL de la vista.
     * @param resourceBundle Recursos asociados a la vista.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (usuarioSelected != null) {
            cargarData();
        }
        cargarImagen("/imagenes/contacto-3d.png", imagenContacto);
    }



    /**
     * Carga los datos del usuario en la interfaz de usuario.
     */
    private void cargarData() {
        Usuario usuario = usuarioSelected;
        nombretxt.setText(usuario.getNombre());
        apellidotxt.setText(usuario.getApellido());
        telefonotxt.setText(usuario.getTelefono());
        cumpleaniosDate.setValue(usuario.getFechaCumpleanios());
        emailtxt.setText(usuario.getCorreo());
    }



    /**
     * Permite al usuario seleccionar una imagen de perfil desde su dispositivo.
     *
     * @param event Evento de acción del botón.
     */
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
                mostrarAlerta("No se pudo cargar la imagen seleccionada.", Alert.AlertType.ERROR);
            }
        }
    }



    /**
     * Confirma la creación o edición del usuario y cierra la vista.
     *
     * @param event Evento de acción del botón.
     */
    @FXML
    void confirmarButtonAction(ActionEvent event) {
        if (usuarioSelected != null) {
            editarUsuario(usuarioSelected);
        } else {
            agregarUsuario();
        }
        cerrarView();
    }



    /**
     * Cierra la ventana actual.
     */
    private void cerrarView() {
        Stage stage = (Stage) cancelarButton.getScene().getWindow();
        stage.close();
    }



    /**
     * Edita un usuario existente en el sistema.
     *
     * @param usuarioSeleccionado Usuario que se desea editar.
     */
    private void editarUsuario(Usuario usuarioSeleccionado) {
        Usuario usuarioEditado = new Usuario(
                nombretxt.getText(),
                apellidotxt.getText(),
                telefonotxt.getText(),
                cumpleaniosDate.getValue(),
                emailtxt.getText(),
                imagenContacto.getImage().toString()
        );
        try {
            if (!gestor.confirmarEditarUsuario(usuarioEditado)) {
                gestor.editarUsuario(usuarioSeleccionado, usuarioEditado);
                mostrarAlerta("Se ha editado el contacto.", Alert.AlertType.INFORMATION);
            }
        } catch (Exception e) {
            mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }



    /**
     * Agrega un nuevo usuario al sistema.
     */
    private void agregarUsuario() {
        try {
            if (!gestor.confirmarUsuario(
                    nombretxt.getText(),
                    apellidotxt.getText(),
                    telefonotxt.getText(),
                    cumpleaniosDate.getValue(),
                    emailtxt.getText(),
                    imagenContacto.getImage().toString()
            )) {
                gestor.crearContacto(
                        nombretxt.getText(),
                        apellidotxt.getText(),
                        telefonotxt.getText(),
                        cumpleaniosDate.getValue(),
                        emailtxt.getText(),
                        imagenContacto.getImage().toString()
                );
                mostrarAlerta("Se ha agregado el contacto.", Alert.AlertType.INFORMATION);
            }
        } catch (Exception e) {
            mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }



    /**
     * Limpia los campos del formulario.
     *
     * @param event Evento de acción del botón.
     */
    @FXML
    private void limpiarButtonAction(ActionEvent event) {
        limpiarCasillas();
    }



    /**
     * Restablece los valores de los campos de entrada.
     */
    private void limpiarCasillas() {
        nombretxt.clear();
        apellidotxt.clear();
        telefonotxt.clear();
        cumpleaniosDate.setValue(null);
        emailtxt.clear();
    }
}
