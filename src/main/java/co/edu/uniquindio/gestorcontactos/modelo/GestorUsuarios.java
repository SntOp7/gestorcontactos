/**
 * Paquete que contiene las clases del modelo para la gestión de contactos.
 */
package co.edu.uniquindio.gestorcontactos.modelo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * Clase encargada de gestionar los usuarios del sistema.
 * Permite crear, buscar, editar y eliminar usuarios.
 * Utiliza anotaciones de Lombok para generar getters, setters y constructor sin argumentos.
 */
@Setter
@Getter
@NoArgsConstructor
public class GestorUsuarios {

    /**
     * Lista que almacena todos los usuarios registrados en el sistema.
     */
    public LinkedList<Usuario> listaUsuarios = new LinkedList<>();

    /**
     * Crea un nuevo contacto y lo agrega a la lista de usuarios si no existe previamente.
     *
     * @param nombre          Nombre del usuario.
     * @param apellido        Apellido del usuario.
     * @param telefono        Número de teléfono del usuario.
     * @param fechaCumpleanios Fecha de cumpleaños del usuario.
     * @param correo          Correo electrónico del usuario.
     * @throws IllegalArgumentException Si algún campo obligatorio es nulo o vacío.
     * @throws IllegalArgumentException Si ya existe un usuario con el mismo teléfono.
     */
    public void crearContacto(String nombre, String apellido, String telefono, LocalDate fechaCumpleanios, String correo, String rutaImagenPerfil) throws Exception {
        confirmarUsuario(nombre, apellido, telefono, fechaCumpleanios, correo, rutaImagenPerfil);
        Usuario usuario = new Usuario(nombre, apellido, telefono, fechaCumpleanios, correo,rutaImagenPerfil);
        listaUsuarios.add(usuario);
    }

    public void reemplazarUsuario(Usuario nuevoUsuario) {
        listaUsuarios.replaceAll(usuario ->
                usuario.getNombre().equalsIgnoreCase(nuevoUsuario.getNombre()) &&
                        usuario.getApellido().equalsIgnoreCase(nuevoUsuario.getApellido())
                        ? nuevoUsuario : usuario
        );
    }

    /**
     * Busca un usuario en la lista basado en su nombre.
     *
     * @param nombre Nombre del usuario a buscar.
     * @return El usuario encontrado o null si no existe.
     * @throws IllegalArgumentException Si el nombre es nulo o vacío.
     */
    public Usuario buscarUsuarioNombre(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser vacio");
        } else if (!nombre.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñÜü][A-Za-zÁÉÍÓÚáéíóúÑñÜü0-9 .'-]*$")) {
            throw new IllegalArgumentException("Formato del nombre no es valido");
        }
        return listaUsuarios.stream()
                .filter(usuario -> usuario.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }

    /**
     * Busca un usuario en la lista basado en su apellido.
     *
     * @param apellido Apellido del usuario a buscar.
     * @return El usuario encontrado o null si no existe.
     * @throws IllegalArgumentException Si el apellido es nulo o vacío.
     */
    public Usuario buscarUsuarioApellido(String apellido) {
        if (apellido == null || apellido.isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede ser vacio");
        } else if (!apellido.matches("[A-Za-zÁÉÍÓÚáéíóúñÑüÜ]+( [A-Za-zÁÉÍÓÚáéíóúñÑüÜ]+)*")) {
            throw new IllegalArgumentException("Formato del apellido no es valido");
        }
        return listaUsuarios.stream()
                .filter(usuario -> usuario.getApellido().equalsIgnoreCase(apellido))
                .findFirst()
                .orElse(null);
    }

    /**
     * Busca un usuario en la lista basado en su nombre completo (nombre y apellido).
     *
     * @param nombreCompleto Nombre completo del usuario (formato: "nombre apellido")
     * @return El usuario encontrado o null si no existe.
     * @throws IllegalArgumentException Si el nombre completo es nulo o vacío.
     */
    public Usuario buscarUsuarioNombreApellido(String nombreCompleto) {
        if (nombreCompleto == null || nombreCompleto.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre completo no puede estar vacío.");
        }
        String[] palabras = nombreCompleto.trim().split("\\s+");
        String nombre = palabras[0];
        String apellido = palabras[1];
        return listaUsuarios.stream()
                .filter(usuario -> usuario.getNombre().equalsIgnoreCase(nombre) &&
                        usuario.getApellido().equalsIgnoreCase(apellido))
                .findFirst()
                .orElse(null);
    }

    /**
     * Busca un usuario en la lista basado en su número de teléfono.
     *
     * @param telefono Número de teléfono del usuario a buscar.
     * @return El usuario encontrado o null si no existe.
     * @throws IllegalArgumentException Si el teléfono es nulo o vacío.
     */
    public Usuario buscarUsuarioTelefono(String telefono) {
        if (telefono == null || telefono.isEmpty()) {
            throw new IllegalArgumentException("El telefono no puede ser vacio");
        } else if (!telefono.matches("3\\d{9}")) {
            throw new IllegalArgumentException("Formato del telefono no es valido");
        }
        return listaUsuarios.stream()
                .filter(usuario -> usuario.getTelefono().equals(telefono))
                .findFirst()
                .orElse(null);
    }

    /**
     * Edita los datos de un usuario existente en la lista.
     *
     * @param usuarioSeleccionado Usuario original que se va a editar.
     * @param usuarioEditado Usuario con los nuevos datos.
     * @throws NullPointerException Si el usuario es nulo.
     * @throws Exception Si el usuario no existe en la lista.
     * @throws IllegalArgumentException Si algún campo obligatorio del usuario es nulo o vacío.
     */
    public void editarUsuario(Usuario usuarioSeleccionado, Usuario usuarioEditado) throws Exception {
        confirmarEditarUsuario(usuarioEditado);
        if (usuarioSeleccionado.getTelefono().equals(usuarioEditado.getTelefono())) {
            usuarioEditado.setNombre(usuarioEditado.getNombre());
            usuarioEditado.setApellido(usuarioEditado.getApellido());
            usuarioEditado.setFechaCumpleanios(usuarioEditado.getFechaCumpleanios());
            usuarioEditado.setCorreo(usuarioEditado.getCorreo());
            usuarioEditado.setRutaImagenPerfil(usuarioEditado.getRutaImagenPerfil());
            reemplazarUsuario(usuarioSeleccionado, usuarioEditado);
        } else if (buscarUsuarioTelefono(usuarioEditado.getTelefono()) != null) {
            throw new IllegalArgumentException("Ya existe un usuario con el mismo teléfono.");
        } else {
            usuarioEditado.setNombre(usuarioEditado.getNombre());
            usuarioEditado.setApellido(usuarioEditado.getApellido());
            usuarioEditado.setTelefono(usuarioEditado.getTelefono());
            usuarioEditado.setFechaCumpleanios(usuarioEditado.getFechaCumpleanios());
            usuarioEditado.setCorreo(usuarioEditado.getCorreo());
            usuarioEditado.setRutaImagenPerfil(usuarioEditado.getRutaImagenPerfil());
            reemplazarUsuario(usuarioSeleccionado, usuarioEditado);
        }
    }

    /**
     * Reemplaza un usuario original por un usuario editado en la lista de usuarios.
     *
     * @param original Usuario original a ser reemplazado.
     * @param editado Usuario con los nuevos datos.
     */
    public void reemplazarUsuario(Usuario original, Usuario editado) {
        listaUsuarios = listaUsuarios.stream()
                .map(usuario -> usuario.getTelefono().equals(original.getTelefono()) ? editado : usuario)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Valida que los datos del usuario a editar sean correctos.
     *
     * @param usuarioEditado Usuario con los datos a validar.
     * @return true si existe un usuario con el mismo nombre y apellido, false en caso contrario.
     * @throws IllegalArgumentException Si algún campo no cumple con las validaciones requeridas.
     */
    public boolean confirmarEditarUsuario(Usuario usuarioEditado) throws IllegalArgumentException {
        boolean repetido = false;
        if (usuarioEditado.getNombre() == null || usuarioEditado.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El campo 'nombre' no puede estar vacío.");
        }
        if (usuarioEditado.getApellido() == null || usuarioEditado.getApellido().isEmpty()) {
            throw new IllegalArgumentException("El campo 'apellido' no puede estar vacío.");
        }
        if (usuarioEditado.getTelefono() == null || usuarioEditado.getTelefono().isEmpty() || !usuarioEditado.getTelefono().matches("3\\d{9}")) {
            throw new IllegalArgumentException("El campo 'teléfono' debe empezar por 3 y tener 10 dígitos.");
        }
        if (usuarioEditado.getFechaCumpleanios() == null || usuarioEditado.getFechaCumpleanios().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de cumpleaños es inválida.");
        }
        if (usuarioEditado.getCorreo() == null || usuarioEditado.getCorreo().isEmpty() || !usuarioEditado.getCorreo().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("El campo 'correo' debe ser una dirección válida.");
        }
        if (buscarUsuarioNombre(usuarioEditado.getNombre()) != null && buscarUsuarioApellido(usuarioEditado.getApellido()) != null) {
            repetido = true;
        }
        return repetido;
    }

    /**
     * Elimina un usuario de la lista.
     *
     * @param usuario Usuario a eliminar.
     * @throws NullPointerException Si el usuario es nulo.
     * @throws Exception Si el usuario no existe en la lista.
     */
    public void eliminarUsuario(Usuario usuario) throws Exception {
        if (usuario == null) {
            throw new NullPointerException("El usuario no puede ser nulo.");
        }
        if (!listaUsuarios.contains(usuario)) {
            throw new Exception("El usuario no existe en la lista.");
        }
        listaUsuarios.remove(usuario);
    }

    /**
     * Verifica que los datos del usuario sean válidos y que no haya duplicados.
     *
     * @param nombre Nombre del usuario.
     * @param apellido Apellido del usuario.
     * @param telefono Número de teléfono del usuario.
     * @param fechaCumpleanios Fecha de cumpleaños del usuario.
     * @param correo Correo electrónico del usuario.
     * @param rutaImagenPerfil Ruta de la imagen de perfil del usuario.
     * @return true si existe un usuario con el mismo nombre y apellido, false en caso contrario.
     * @throws IllegalArgumentException Si algún campo es nulo, vacío o no cumple con los requisitos.
     */
    public boolean confirmarUsuario(String nombre, String apellido, String telefono, LocalDate fechaCumpleanios, String correo, String rutaImagenPerfil) throws Exception {
        boolean repetido = false;
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El campo 'nombre' no puede estar vacío.");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new IllegalArgumentException("El campo 'apellido' no puede estar vacío.");
        }
        if (telefono == null || telefono.isEmpty() || !telefono.matches("3\\d{9}")) {
            throw new IllegalArgumentException("El campo 'teléfono' debe empezar por 3 y tener 10 dígitos.");
        }
        if (fechaCumpleanios == null || fechaCumpleanios.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de cumpleaños es invalida.");
        }
        if (correo == null || correo.isEmpty() || !correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("El campo 'correo' debe ser una dirección válida.");
        }
        if (buscarUsuarioTelefono(telefono) != null) {
            throw new IllegalArgumentException("Ya existe un usuario con el mismo teléfono.");
        }
        if (buscarUsuarioNombre(nombre) != null && buscarUsuarioApellido(apellido) != null) {
            repetido = true;
        }
        return repetido;
    }
}