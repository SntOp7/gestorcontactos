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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;

import javafx.event.ActionEvent;


import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PrincipalController extends Controller implements Initializable {

        @FXML
        private TextField buscarContactoTxt;

        @FXML
        private TableView<Usuario> tblContactos;

        @FXML
        private TableColumn<Usuario, String> tcNombre;

        @FXML
        private TableColumn<Usuario, String> tcCorreo;

        @FXML
        private TableColumn<Usuario, String> tcTelefono;

        @FXML
        private ComboBox<Filtrado> filtrarBox;

        @FXML
        private TableColumn<Usuario, String> tcCumpleanios;

        @FXML
        private ComboBox<Opciones> opcionesBox;

        @FXML
        private TableColumn<Usuario, String> tcApellido;

        @FXML
        private ImageView busquedaImagen;

        @Setter
        private App app;

        private ObservableList<Usuario> usuarios;
        private ObservableList<Filtrado> filtrados;
        private ObservableList<Opciones> opciones;


        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
                usuarios = FXCollections.observableArrayList();
                filtrados = FXCollections.observableArrayList();
                opciones = FXCollections.observableArrayList();
                initData();
                initTable();
                seleccionarUsuario();
        }

        private void initData() {
                if (app != null) {
                        usuarios.addAll(app.gestor.getListaUsuarios());
                }
                filtrados.add(Filtrado.NOMBRE);
                filtrados.add(Filtrado.TELEFONO);
                opciones.add(Opciones.AGREGAR);
                opciones.add(Opciones.ELIMINAR);
                opciones.add(Opciones.EDITAR);
                opcionesBox.setItems(opciones);
                filtrarBox.setItems(filtrados);
                tblContactos.setItems(usuarios);
        }

        private void initTable() {
                tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
                tcApellido.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido()));
                tcTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));
                tcCumpleanios.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaCumpleanios().toString()));
                tcCorreo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCorreo()));
        }

        private void seleccionarUsuario() {
                tblContactos.setOnMouseClicked(e -> setUsuarioSelected(tblContactos.getSelectionModel().getSelectedItem()));
        }


        @FXML
        void aceptarAction(ActionEvent event) throws Exception{
                Opciones opcion = opcionesBox.getSelectionModel().getSelectedItem();
                if (opcion == Opciones.AGREGAR) {
                        app.openContactoView();
                } else if (opcion == Opciones.ELIMINAR || opcion == Opciones.EDITAR) {
                        Usuario usuario = tblContactos.getSelectionModel().getSelectedItem();
                        super.setUsuarioSelected(usuario);
                        if (usuario != null) {
                                if (opcion == Opciones.ELIMINAR) {
                                        app.gestor.eliminarUsuario(usuario);
                                } else {
                                        app.openContactoView();
                                }
                        } else {
                                super.mostrarAlerta("Debe seleccionar un contacto", Alert.AlertType.ERROR);
                        }
                } else {
                        super.mostrarAlerta("Debe seleccionar un opcion.", Alert.AlertType.ERROR);
                }
                usuarios.setAll(app.gestor.getListaUsuarios());
                tblContactos.setItems(usuarios);
        }

        /*@FXML
        void buscarAction(ActionEvent event) {
                Filtrado filtrado = filtrarBox.getSelectionModel().getSelectedItem();
                String argumento = buscarContactoTxt.getText();
                Usuario usuario = null;
                if (filtrado == Filtrado.NOMBRE) {

                } else if (filtrado == Filtrado.TELEFONO) {
                       usuario = app.gestor.buscarUsuarioApellido(argumento);
                       tblContactos.getSelectionModel().select(usuario);
                } else if (filtrado == null) {
                        super.mostrarAlerta("Debe seleccionar un filtrado.", Alert.AlertType.ERROR);
                } else if (usuario == null) {
                        super.mostrarAlerta("No se encontro el usuario.", Alert.AlertType.ERROR);
                }
        }*/

        @FXML
        void buscarAction(MouseEvent event) {
                Filtrado filtrado = filtrarBox.getSelectionModel().getSelectedItem(); // Obtener el tipo de filtro
                String argumento = buscarContactoTxt.getText().trim(); // Obtener el texto ingresado

                if (argumento.isEmpty()) {
                        super.mostrarAlerta("Debe ingresar un nombre, apellido o teléfono.", Alert.AlertType.ERROR);
                        return;
                }

                if (filtrado == null) {
                        super.mostrarAlerta("Debe seleccionar un método de filtrado.", Alert.AlertType.ERROR);
                        return;
                }

                buscarUsuario(filtrado, app.gestor);
        }

        public void buscarUsuario(Filtrado filtrado, GestorUsuarios gestorUsuarios) {
                try {
                        String filtro = buscarContactoTxt.getText().trim();

                        if (filtro.isEmpty()) {
                                super.mostrarAlerta("Ingrese un nombre, apellido o teléfono.", Alert.AlertType.WARNING);
                                return;
                        }

                        Usuario usuarioEncontrado = null;

                        if (filtrado == Filtrado.TELEFONO) {
                                if (!filtro.matches("\\d+")) {
                                        super.mostrarAlerta("El teléfono debe contener solo números.", Alert.AlertType.ERROR);
                                        return;
                                }
                                usuarioEncontrado = gestorUsuarios.buscarUsuarioTelefono(filtro);
                        } else if (filtrado == Filtrado.NOMBRE) {
                                String[] palabras = filtro.split("\\s+");

                                if (palabras.length == 1) {
                                        usuarioEncontrado = gestorUsuarios.buscarUsuarioNombre(filtro);
                                } else if (palabras.length == 2) {
                                        usuarioEncontrado = gestorUsuarios.buscarUsuarioApellido(palabras[1]);
                                } else {
                                        super.mostrarAlerta("Ingrese solo un nombre o un apellido.", Alert.AlertType.WARNING);
                                        return;
                                }
                        }

                        if (usuarioEncontrado != null) {
                                tblContactos.getSelectionModel().clearSelection();
                                tblContactos.getSelectionModel().select(usuarioEncontrado);
                                tblContactos.scrollTo(usuarioEncontrado);
                        } else {
                                super.mostrarAlerta("No se encontró el usuario.", Alert.AlertType.INFORMATION);
                        }

                } catch (IllegalArgumentException e) {
                        super.mostrarAlerta("Error: " + e.getMessage(), Alert.AlertType.ERROR);
                }

        }
}
