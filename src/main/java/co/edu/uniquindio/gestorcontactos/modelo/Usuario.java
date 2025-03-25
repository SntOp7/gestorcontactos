package co.edu.uniquindio.gestorcontactos.modelo;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor

public class Usuario {
    String nombre;
    String apellido;
    String telefono;
    LocalDate fechaCumpleanios;
    String correo;
    private String rutaImagenPerfil;
}
