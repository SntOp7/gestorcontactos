package co.edu.uniquindio.gestorcontactos.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedList;

@NoArgsConstructor
public class GestorUsuarios {
    @Getter
    @Setter
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
     * @throws IllegalArgumentException Si ya existe un usuario con el mismo nombre o teléfono.
     */
    public void crearContacto(String nombre, String apellido, String telefono, LocalDate fechaCumpleanios, String correo) throws Exception {
        confirmarUsuario(nombre, apellido, telefono, fechaCumpleanios, correo);
        Usuario usuario = new Usuario(nombre, apellido, telefono, fechaCumpleanios, correo);
        listaUsuarios.add(usuario);
    }


    /**
     * Busca un usuario en la lista basado en su nombre.
     *
     * @param nombre Nombre del usuario a buscar.
     * @return El usuario encontrado o null si no existe.
     * @throws IllegalArgumentException Si el nombre es nulo o vacío.
     * @throws NullPointerException Si la lista de usuarios no ha sido inicializada.
     */
    public Usuario buscarUsuarioNombre(String nombre) {
        return listaUsuarios.stream()
                .filter(usuario -> usuario.getNombre().equals(nombre))
                .findFirst()
                .orElse(null);
    }

    /**
     * Busca un usuario en la lista basado en su nombre.
     *
     * @param nombre Nombre del usuario a buscar.
     * @return El usuario encontrado o null si no existe.
     * @throws IllegalArgumentException Si el nombre es nulo o vacío.
     * @throws NullPointerException Si la lista de usuarios no ha sido inicializada.
     */
    public Usuario buscarUsuarioApellido(String apellido) {
        return listaUsuarios.stream()
                .filter(usuario -> usuario.getApellido().equals(apellido))
                .findFirst()
                .orElse(null);
    }

    /**
     * Busca un usuario en la lista basado en su número de teléfono.
     *
     * @param telefono Número de teléfono del usuario a buscar.
     * @return El usuario encontrado o null si no existe.
     * @throws IllegalArgumentException Si el teléfono es nulo o vacío.
     * @throws NullPointerException Si la lista de usuarios no ha sido inicializada.
     */
    public Usuario buscarUsuarioTelefono(String telefono) {
        return listaUsuarios.stream()
                .filter(usuario -> usuario.getTelefono().equals(telefono))
                .findFirst()
                .orElse(null);
    }

    /**
     * Edita los datos de un usuario existente en la lista.
     *
     * @param usuario Usuario a editar.
     * @throws NullPointerException Si el usuario es nulo o la lista de usuarios no ha sido inicializada.
     * @throws Exception Si el usuario no existe en la lista.
     * @throws IllegalArgumentException Si el nombre o teléfono del usuario es nulo o vacío.
     */
    public void editarUsuario(Usuario usuario) throws Exception {
        if (usuario == null) {
            throw new NullPointerException("El usuario no puede ser nulo.");
        }
        confirmarUsuario(usuario.getNombre(), usuario.getApellido(), usuario.getTelefono(), usuario.getFechaCumpleanios(), usuario.getCorreo());
        listaUsuarios.set(listaUsuarios.indexOf(usuario), usuario);
    }

    /**
     * Elimina un usuario de la lista.
     *
     * @param usuario Usuario a eliminar.
     * @throws NullPointerException Si el usuario es nulo o la lista de usuarios no ha sido inicializada.
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

    public void confirmarUsuario(String nombre, String apellido, String telefono, LocalDate fechaCumpleanios, String correo) throws Exception {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("Verifique el campo: nombre.");
        }

        if (apellido == null || apellido.isEmpty()) {
            throw new IllegalArgumentException("Verifique el campo: apellido.");
        }

        if (telefono == null || telefono.isEmpty()) {
            throw new IllegalArgumentException("Verifique el campo: teléfono.");
        }

        if (fechaCumpleanios == null || fechaCumpleanios.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Verifique el campo: fecha de cumpleaños.");
        }

        if (correo == null || correo.isEmpty()) {
            throw new IllegalArgumentException("Verifique el campo: correo.");
        }

        if (buscarUsuarioTelefono(telefono) != null) {
            throw new IllegalArgumentException("Ya existe un usuario con el mismo telefono.");
        }

        if (buscarUsuarioNombre(nombre) != null && buscarUsuarioApellido(apellido) != null) {
            throw new IllegalArgumentException();
        }
    }
}
