package co.edu.uniquindio.gestorcontactos.modelo;

import java.time.LocalDate;
import java.util.LinkedList;

public class GestorUsuarios {
    public LinkedList<Usuario> listaUsuarios;
    public GestorUsuarios(){
        listaUsuarios = new LinkedList<>();
    }

    public void crearContacto(String nombre, String apellido, String telefono, LocalDate fechaCumpleanios, String correo) throws Exception{
        Usuario usuarioBuscado = buscarUsuarioNombre(nombre);
        if (usuarioBuscado == null){
            usuarioBuscado = buscarUsuarioTelefono(telefono);
        }

        if(usuarioBuscado!=null){
            throw new Exception("Ya existe un usuario con el mismo ID");
        }else{
            Usuario usuario = new Usuario(nombre, apellido, telefono, fechaCumpleanios, correo);
            listaUsuarios.add(usuario);
        }
    }

    public Usuario buscarUsuarioTelefono(String telefono){
        return listaUsuarios.stream()
                .filter(usuario -> usuario.getTelefono().equals(telefono))
                .findFirst()
                .orElse(null);
    }

    public Usuario buscarUsuarioNombre(String nombre){
        return listaUsuarios.stream()
                .filter(usuario -> usuario.getNombre().equals(nombre))
                .findFirst()
                .orElse(null);
    }

    public void editarUsuario(Usuario usuario) throws Exception{
        Usuario usuarioBuscado = buscarUsuarioNombre(usuario.getNombre());
        if (usuarioBuscado == null){
            usuarioBuscado = buscarUsuarioTelefono(usuario.getTelefono());
        }

        if(usuarioBuscado!=null){
            usuarioBuscado.setNombre(usuario.getNombre());
            usuarioBuscado.setApellido(usuario.getApellido());
            usuarioBuscado.setTelefono(usuario.getTelefono());
        }else{
            throw new Exception("No existe un usuario con el nombre dado");
        }
    }

    public void eliminarUsuario(String nombre, String telefono) throws Exception{
        Usuario usuarioBuscado = buscarUsuarioNombre(nombre);
        if (usuarioBuscado == null){
            usuarioBuscado = buscarUsuarioTelefono(telefono);
        }

        if(usuarioBuscado==null){
            throw new Exception("No existe un usuario con el nombre dado");
        }else{
            listaUsuarios.remove(usuarioBuscado);
        }
    }
}
