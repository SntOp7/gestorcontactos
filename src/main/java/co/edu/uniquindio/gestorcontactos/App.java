package co.edu.uniquindio.gestorcontactos;

import co.edu.uniquindio.gestorcontactos.controladores.ContactoController;
import co.edu.uniquindio.gestorcontactos.modelo.GestorUsuarios;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.Principal;

public class App extends Application {

    public GestorUsuarios gestor = new GestorUsuarios();

    private Stage principalStage;
    private Stage contactoStage;

    PrincipalController principalController;
    ContactoController contactoController;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        principalStage = stage;
        principalStage.setTitle("Gestor de Contactos");
        openPrincipalView();
    }

    public void openPrincipalView() throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("interfazPrincipalContacto.fxml"));
        Parent root = loader.load();
        principalController = loader.getController();
        principalController.setApp(this);

        Scene scene = new Scene(root);
        principalStage = new Stage();
        principalStage.setScene(scene);

        principalStage.show();

    }

    public void openContactoView() throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("interfazContacto.fxml"));
        Parent root = loader.load();
        contactoController = loader.getController();
        contactoController.setApp(this);

        Scene scene = new Scene(root);
        contactoStage = new Stage();
        contactoStage.setScene(scene);

        contactoStage.show();

    }
}
