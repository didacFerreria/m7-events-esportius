/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.util.List;
import modelo.Usuario;

public interface UsuarioDAO {
    void agregarUsuario(Usuario usuario);
    List<Usuario> listarUsuarios();
    Usuario buscarUsuarioPorEmail(String email);
    Usuario buscarUsuario(String nombre);
    void eliminarUsuario(String email);
}
