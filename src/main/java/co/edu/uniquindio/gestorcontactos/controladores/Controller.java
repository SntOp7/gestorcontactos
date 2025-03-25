package co.edu.uniquindio.gestorcontactos.controladores;

import co.edu.uniquindio.gestorcontactos.App;
import co.edu.uniquindio.gestorcontactos.modelo.Usuario;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.net.URL;

public abstract class Controller {
    @Getter
    @Setter
    private Usuario usuarioSelected = null;

    private App app;

    public abstract void setApp(App app);

    public void mostrarAlerta(String mensaje, Alert.AlertType tipo){
        Alert alert = new Alert(tipo);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }



    public void cargarImagen(String imagePath, ImageView imageView) {
        try {
            if (imagePath.startsWith("/") || imagePath.startsWith("resources/")) {
                URL imageUrl = getClass().getResource(imagePath);
                if (imageUrl == null) {
                    mostrarAlerta("No se encontró la imagen en el classpath: " + imagePath, Alert.AlertType.ERROR);
                } else {
                    imageView.setImage(new Image(imageUrl.toExternalForm()));
                }
            } else {
                File file = new File(imagePath);
                if (!file.exists()) {
                    mostrarAlerta("No se encontró la imagen en el sistema de archivos: " + imagePath, Alert.AlertType.ERROR);
                } else {
                    imageView.setImage(new Image(file.toURI().toString()));
                }
            }
        } catch (Exception e) {
            mostrarAlerta("No se pudo cargar la imagen: " + imagePath, Alert.AlertType.ERROR);
        }
    }


}
