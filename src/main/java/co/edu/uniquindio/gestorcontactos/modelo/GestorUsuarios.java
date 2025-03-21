package co.edu.uniquindio.gestorcontactos.modelo;

import java.time.LocalDate;
import java.util.LinkedList;

public class GestorUsuarios {
    public LinkedList<Usuario> listaUsuarios;
    public GestorUsuarios(){
        listaUsuarios = new LinkedList<>();
    }

    public void crearContacto(String nombre, String apellido, String telefono, LocalDate fechaCumpleanios, String correo){
        Usuario usuario = new Usuario(nombre,  apellido, telefono, fechaCumpleanios, correo);
        listaUsuarios.add(usuario);
    }

    public void buscarUsuario(String nombre,String telefono){
        return listaUsuarios.stream()
                .filter(billetera -> billetera.getNumero().equals(numero))
                .findFirst()
                .orElse(null);
    }
}
