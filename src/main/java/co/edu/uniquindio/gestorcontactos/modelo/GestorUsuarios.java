package co.edu.uniquindio.gestorcontactos.modelo;

import java.time.LocalDate;
import java.util.LinkedList;

public class GestorUsuarios {
    public LinkedList<Usuario> listaUsuarios;
    public GestorUsuarios(){
        listaUsuarios = new LinkedList<>();
    }

    public void crearContacto(String nombre, String apellido, String telefono, LocalDate fechaCumpleanios, String correo) throws Exception{
        if(nombre == null || nombre.isEmpty()){
            throw new Exception("El nombre es obligatorio");
        }

        if(apellido == null || apellido.isEmpty()){
            throw new Exception("La apellido es obligatoria");
        }

        if(telefono == null || telefono.isEmpty()){
            throw new Exception("El telefono es obligatorio");
        }

        if (fechaCumpleanios == null ){
            throw new Exception("La fecha de cumpleaños es obligatorio");
        }

        if(correo == null || correo.isEmpty()){
            throw new Exception("El correo es obligatoria");
        }

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

    public void editarUsuario(Usuario usuario){
        usuario.setNombre(usuario.getNombre());
        usuario.setApellido(usuario.getApellido());
        usuario.setTelefono(usuario.getTelefono());
        usuario.setFechaCumpleanios(usuario.getFechaCumpleanios());
        usuario.setCorreo(usuario.getCorreo());

    }

    public void eliminarUsuario(Usuario usuario){
        listaUsuarios.remove(usuario);
    }
}
