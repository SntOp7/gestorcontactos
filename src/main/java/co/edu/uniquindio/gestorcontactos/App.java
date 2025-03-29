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

    private Stage principalStage;
    private Stage contactoStage;
    private Stage repetidoStage;

    @Getter
    Controller principalController;
    @Getter
    Controller contactoController;
    @Getter
    Controller repetidoController;

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

    public void openRepetidoView() throws Exception {
        if (repetidoStage == null) {
            repetidoStage = new Stage();
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/gestorcontactos/interfazRepetido.fxml"));
        iniciarView(repetidoStage, repetidoController, loader);
    }


    public void iniciarView(Stage stage, Controller controller, FXMLLoader loader) throws Exception {
        Parent root = loader.load();
        controller = loader.getController();
        controller.setApp(this);

        Scene scene = new Scene(root);
        stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
