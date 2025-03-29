package co.edu.uniquindio.gestorcontactos.controladores;

import co.edu.uniquindio.gestorcontactos.App;
import co.edu.uniquindio.gestorcontactos.modelo.GestorUsuarios;
import co.edu.uniquindio.gestorcontactos.modelo.Usuario;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;



/**
 * Clase base para los controladores de la aplicación.
 * Proporciona funcionalidades comunes como gestión de alertas y carga de imágenes.
 */
public abstract class Controller {




    /** Usuario seleccionado en la aplicación. */
    public static Usuario usuarioSelected = null;




    /** Gestor de usuarios que maneja las operaciones de contacto. */
    public static GestorUsuarios gestor = new GestorUsuarios();




    /** Instancia de la aplicación principal. */
    private App app;




    /**
     * Método abstracto para establecer la instancia de la aplicación.
     * Debe ser implementado por las subclases.
     *
     * @param app Instancia de la aplicación principal.
     */
    public abstract void setApp(App app);




    /**
     * Muestra una alerta emergente con un mensaje especificado.
     *
     * @param mensaje Contenido del mensaje de la alerta.
     * @param tipo Tipo de alerta (INFORMATION, WARNING, ERROR, etc.).
     */
    public void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }




    /**
     * Carga una imagen en un ImageView desde una ruta local o del proyecto.
     *
     * @param imagePath Ruta de la imagen a cargar.
     * @param imageView Componente donde se mostrará la imagen.
     */
    public void cargarImagen(String imagePath, ImageView imageView) {
        if (imagePath == null || imageView == null) {
            return;
        }

        // Verifica si la ruta es interna (desde recursos) o un archivo externo
        if (imagePath.startsWith("/") || imagePath.startsWith("resources/")) {
            URL imageUrl = getClass().getResource(imagePath);
            if (imageUrl != null) {
                imageView.setImage(new Image(imageUrl.toExternalForm()));
            }
        } else {
            File file = new File(imagePath);
            if (file.exists()) {
                imageView.setImage(new Image(file.toURI().toString()));
            }
        }
    }
}
