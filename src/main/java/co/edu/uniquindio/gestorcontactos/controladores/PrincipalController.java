package co.edu.uniquindio.gestorcontactos.controladores;

import co.edu.uniquindio.gestorcontactos.App;
import co.edu.uniquindio.gestorcontactos.modelo.Usuario;
import co.edu.uniquindio.gestorcontactos.modelo.enums.Filtrado;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;

import java.awt.event.ActionEvent;
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
        private ComboBox<?> filtrarBox;

        @FXML
        private TableColumn<Usuario, String> tcCumpleanios;

        @FXML
        private ComboBox<?> opcionesBox;

        @FXML
        private TableColumn<Usuario, String> tcApellido;

        @Setter
        private App app;

        private ObservableList<Usuario> usuarios;
        private ObservableList<Filtrado> filtrados;

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
                usuarios = FXCollections.observableArrayList();
                filtrados = FXCollections.observableArrayList();
                usuarios.addAll(app.gestor.getListaUsuarios());
                filtrados.add(Filtrado.NOMBRE);
                filtrados.add(Filtrado.TELEFONO);
                filtrados.add(Filtrado.NINGUNO);
                initTable();
                filtrarBox.setItems;
                tblContactos.setItems(usuarios);
                seleccionarUsuario();
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
}
