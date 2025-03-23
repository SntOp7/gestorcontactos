package co.edu.uniquindio.gestorcontactos.controladores;

import co.edu.uniquindio.gestorcontactos.App;
import co.edu.uniquindio.gestorcontactos.modelo.Usuario;
import javafx.scene.control.Alert;
import lombok.Getter;
import lombok.Setter;

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
}
