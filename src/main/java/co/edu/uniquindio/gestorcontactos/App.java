package co.edu.uniquindio.gestorcontactos;

import co.edu.uniquindio.gestorcontactos.controladores.ContactoController;
import co.edu.uniquindio.gestorcontactos.controladores.Controller;
import co.edu.uniquindio.gestorcontactos.controladores.PrincipalController;
import co.edu.uniquindio.gestorcontactos.modelo.GestorUsuarios;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import  lombok.Setter;

import java.io.IOException;
import java.security.Principal;

public class App extends Application {

    public GestorUsuarios gestor = new GestorUsuarios();

    private Stage principalStage;
    private Stage contactoStage;

    @Getter
    Controller principalController;
    @Getter
    Controller contactoController;

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
        iniciarView(principalStage, principalController, loader);
    }

    public void openContactoView() throws Exception {
        if (contactoStage == null) {
            contactoStage = new Stage();
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/gestorcontactos/interfazContacto.fxml"));
        iniciarView(contactoStage, contactoController, loader);
    }

    /*public      void iniciarView(Stage stage, Controller controller, FXMLLoader loader) throws Exception {
        Parent root = loader.load();
        controller = loader.getController();
        controller.setApp(this);

        Scene scene = new Scene(root);
        stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }*/

    public void iniciarView(Stage stage, Controller controller, FXMLLoader loader) throws Exception {
        Parent root = loader.load();
        controller = loader.getController(); // Obtener la instancia real del controlador

        if (controller instanceof PrincipalController) {
            principalController = (PrincipalController) controller; // Asignar correctamente
        } else if (controller instanceof ContactoController) {
            contactoController = (ContactoController) controller; // Asignar correctamente
        }

        controller.setApp(this); // Asignar la instancia de App

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
