/**
 * Paquete que contiene los controladores de la aplicación.
 */
package co.edu.uniquindio.gestorcontactos.controladores;

import co.edu.uniquindio.gestorcontactos.App;
import co.edu.uniquindio.gestorcontactos.modelo.Usuario;
import co.edu.uniquindio.gestorcontactos.modelo.GestorUsuarios;
import co.edu.uniquindio.gestorcontactos.modelo.enums.Filtrado;
import co.edu.uniquindio.gestorcontactos.modelo.enums.Opciones;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import lombok.Setter;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;




/**
 * Controlador principal que maneja la vista de la aplicación y la gestión de contactos.
 */
public class PrincipalController extends Controller implements Initializable {

        @FXML
        private TextField buscarContactoTxt; // Campo de texto para búsqueda de contactos

        @FXML
        private TableView<Usuario> tblContactos; // Tabla para visualizar los contactos

        @FXML
        private TableColumn<Usuario, String> tcNombre; // Columna para mostrar el nombre del contacto

        @FXML
        private TableColumn<Usuario, String> tcCorreo; // Columna para mostrar el correo electrónico del contacto

        @FXML
        private TableColumn<Usuario, String> tcTelefono; // Columna para mostrar el teléfono del contacto

        @FXML
        private ComboBox<Filtrado> filtrarBox; // ComboBox para seleccionar el tipo de filtrado

        @FXML
        private TableColumn<Usuario, String> tcCumpleanios; // Columna para mostrar la fecha de cumpleaños del contacto

        @FXML
        private ComboBox<Opciones> opcionesBox; // ComboBox para seleccionar la acción a realizar

        @FXML
        private TableColumn<Usuario, String> tcApellido; // Columna para mostrar el apellido del contacto

        @FXML
        private ImageView busquedaImagen; // Imagen de búsqueda

        @Setter
        private App app; // Referencia a la aplicación principal

        private ObservableList<Usuario> usuarios;
        private ObservableList<Filtrado> filtrados;
        private ObservableList<Opciones> opciones;




        /**
         * Inicializa el controlador, configurando datos y eventos iniciales.
         */
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
                usuarios = FXCollections.observableArrayList();
                filtrados = FXCollections.observableArrayList();
                opciones = FXCollections.observableArrayList();
                initData();
                initTable();
                seleccionarUsuario();
        }




        /**
         * Inicializa los datos de filtrado y opciones disponibles.
         */
        private void initData() {
                filtrados.add(Filtrado.NOMBRE);
                filtrados.add(Filtrado.TELEFONO);
                opciones.add(Opciones.AGREGAR);
                opciones.add(Opciones.ELIMINAR);
                opciones.add(Opciones.EDITAR);
                opcionesBox.setItems(opciones);
                filtrarBox.setItems(filtrados);
                actualizarTabla();
        }




        /**
         * Configura las columnas de la tabla para visualizar los contactos.
         */
        private void initTable() {
                tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
                tcApellido.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido()));
                tcTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));
                tcCumpleanios.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaCumpleanios().toString()));
                tcCorreo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCorreo()));
        }




        /**
         * Permite seleccionar un usuario de la tabla.
         */
        private void seleccionarUsuario() {
                tblContactos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                        usuarioSelected = newSelection;
                });
        }




        /**
         * Maneja la acción del botón de aceptar según la opción seleccionada.
         */
        @FXML
        void aceptarAction(ActionEvent event) throws Exception {
                Opciones opcion = opcionesBox.getSelectionModel().getSelectedItem();
                opcionesBox.setPromptText("Opciones de Contacto");
                if (opcion == Opciones.AGREGAR) {
                        tblContactos.getSelectionModel().clearSelection();
                        app.openContactoView();
                } else if (opcion == Opciones.ELIMINAR || opcion == Opciones.EDITAR) {
                        seleccionarUsuario();
                        Usuario usuario = usuarioSelected;
                        if (usuario != null) {
                                if (opcion == Opciones.ELIMINAR) {
                                        gestor.eliminarUsuario(usuario);
                                        super.mostrarAlerta("Se ha eliminado el contacto.", Alert.AlertType.INFORMATION);
                                } else {
                                        app.openContactoView();
                                }
                        } else {
                                super.mostrarAlerta("Debe seleccionar un contacto.", Alert.AlertType.ERROR);
                        }
                } else {
                        super.mostrarAlerta("Debe escoger una opción.", Alert.AlertType.ERROR);
                }
        }




        /**
         * Maneja la acción de búsqueda de un contacto en la tabla.
         */
        @FXML
        void buscarAction(MouseEvent event) {
                Filtrado filtrado = filtrarBox.getSelectionModel().getSelectedItem();
                filtrarBox.setPromptText("Filtrar");
                String argumento = buscarContactoTxt.getText().trim();
                if (filtrado == null) {
                        super.mostrarAlerta("Debe seleccionar un filtro para la búsqueda.", Alert.AlertType.ERROR);
                }
                buscarUsuario(argumento, filtrado, gestor);
        }




        /**
         * Busca un usuario en la lista según el criterio de filtrado.
         */
        public void buscarUsuario(String argumento, Filtrado filtrado, GestorUsuarios gestorUsuarios) {
                try {
                        Usuario usuarioEncontrado = null;
                        if (filtrado == Filtrado.TELEFONO) {
                                usuarioEncontrado = gestorUsuarios.buscarUsuarioTelefono(argumento);
                        } else if (filtrado == Filtrado.NOMBRE) {
                                usuarioEncontrado = gestorUsuarios.buscarUsuarioNombre(argumento);
                        }
                        if (usuarioEncontrado != null) {
                                tblContactos.getSelectionModel().clearSelection();
                                tblContactos.getSelectionModel().select(usuarioEncontrado);
                                tblContactos.scrollTo(usuarioEncontrado);
                                super.mostrarAlerta("Se ha encontrado el contacto.", Alert.AlertType.INFORMATION);
                        } else {
                                super.mostrarAlerta("No se encontró el contacto.", Alert.AlertType.INFORMATION);
                        }
                } catch (IllegalArgumentException e) {
                        super.mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
                }
        }




        /**
         * Actualiza los datos de la tabla con la lista de usuarios.
         */
        private void actualizarTabla() {
                usuarios.setAll(gestor.getListaUsuarios());
                tblContactos.setItems(usuarios);
                tblContactos.refresh();
        }




        /**
         * Acción para actualizar la tabla de contactos.
         */
        @FXML
        private void observarContactoAction(ActionEvent event){
                actualizarTabla();
                super.mostrarAlerta("Se ha cargado los contactos.", Alert.AlertType.INFORMATION);
        }
}
