/**
 * Paquete que contiene las clases del modelo para la gestión de contactos.
 */
package co.edu.uniquindio.gestorcontactos.modelo;

import lombok.*;
import java.time.LocalDate;

/**
 * Clase que representa un usuario en el sistema de gestión de contactos.
 * Almacena la información personal de un contacto.
 * Utiliza anotaciones de Lombok para generar getters, setters y constructor con todos los argumentos.
 */
@Getter
@Setter
@AllArgsConstructor
public class Usuario {
    /**
     * Nombre del usuario.
     */
    String nombre;

    /**
     * Apellido del usuario.
     */
    String apellido;

    /**
     * Número de teléfono del usuario. Debe comenzar con 3 y tener 10 dígitos.
     */
    String telefono;

    /**
     * Fecha de cumpleaños del usuario. No puede ser posterior a la fecha actual.
     */
    LocalDate fechaCumpleanios;

    /**
     * Correo electrónico del usuario. Debe tener un formato válido.
     */
    String correo;

    /**
     * Ruta donde se almacena la imagen de perfil del usuario.
     */
    private String rutaImagenPerfil;
}