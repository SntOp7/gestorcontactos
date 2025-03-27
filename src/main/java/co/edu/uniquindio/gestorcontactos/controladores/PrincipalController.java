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
                opciones.add(Opciones.OBSERVAR);
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
                tblContactos.setOnMouseClicked(e -> super.setUsuarioSelected(tblContactos.getSelectionModel().getSelectedItem()));
        }

        @FXML
        void aceptarAction(ActionEvent event) throws Exception {
                Opciones opcion = opcionesBox.getSelectionModel().getSelectedItem();

                if (opcion == Opciones.AGREGAR) {
                        app.openContactoView();
                } else if (opcion == Opciones.ELIMINAR || opcion == Opciones.EDITAR) {
                        seleccionarUsuario();
                        Usuario usuario = super.getUsuarioSelected();
                        if (usuario != null) {
                                if (opcion == Opciones.ELIMINAR) {
                                        app.gestor.eliminarUsuario(usuario);
                                        super.mostrarAlerta("Se ha eliminado el contacto.", Alert.AlertType.INFORMATION);
                                } else {
                                        app.openContactoView();
                                }
                        } else {
                                super.mostrarAlerta("Debe seleccionar un contacto.", Alert.AlertType.ERROR);
                        }
                } else if (opcion == Opciones.OBSERVAR) {

                        actualizarTabla();
                } else {
                        super.mostrarAlerta("Debe seleccionar una opción de contacto.", Alert.AlertType.ERROR);
                }

                actualizarTabla();
        }


        public void actualizarTabla() {
                if (app != null && app.gestor != null) {
                        usuarios.setAll(FXCollections.observableArrayList(app.gestor.getListaUsuarios()));
                        tblContactos.setItems(usuarios);
                        tblContactos.refresh();
                }
        }



        @FXML
        void buscarAction(MouseEvent event) {
                Filtrado filtrado = filtrarBox.getSelectionModel().getSelectedItem();
                String argumento = buscarContactoTxt.getText().trim();
                if (filtrado == null) {
                        super.mostrarAlerta("Debe seleccionar un filtro para la busqueda.", Alert.AlertType.ERROR);
                }
                buscarUsuario(argumento, filtrado, app.gestor);
        }

        public void buscarUsuario(String argumento, Filtrado filtrado, GestorUsuarios gestorUsuarios) {
                try {
                        Usuario usuarioEncontrado = null;
                        if (filtrado == Filtrado.TELEFONO) {
                                usuarioEncontrado = gestorUsuarios.buscarUsuarioTelefono(argumento);
                        } else if (filtrado == Filtrado.NOMBRE) {
                                String[] palabras = argumento.split("\\s+");
                                if (palabras.length == 1) {
                                        usuarioEncontrado = gestorUsuarios.buscarUsuarioNombre(argumento);
                                        if (usuarioEncontrado == null) {
                                                usuarioEncontrado = gestorUsuarios.buscarUsuarioApellido(argumento);
                                        }
                                } else if (palabras.length == 2) {
                                        usuarioEncontrado = gestorUsuarios.buscarUsuarioNombreApellido(argumento);
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
                        super.mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
                }
        }


        @FXML
        private void observarContactoAction(ActionEvent event){
                actualizarTabla();
        }
}
