/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia.memoria;

import java.util.ArrayList;
import java.util.List;
import modelo.Usuario;
import persistencia.UsuarioDAO;

public class UsuarioDAOMemoria implements UsuarioDAO {
    private List<Usuario> usuarios = new ArrayList<>();

    @Override
    public void agregarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return new ArrayList<>(usuarios);
    }

    @Override
    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarios.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void eliminarUsuario(String email) {
        usuarios.removeIf(u -> u.getEmail().equalsIgnoreCase(email));
    }
}