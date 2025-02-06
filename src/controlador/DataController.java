/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.util.List;
import modelo.Competicion;
import modelo.Equipo;
import modelo.Jugador;
import modelo.Usuario;
import persistencia.CompeticionDAO;
import persistencia.EquipoDAO;
import persistencia.JugadorDAO;
import persistencia.UsuarioDAO;
import persistencia.memoria.CompeticionDAOMemoria;
import persistencia.memoria.EquipoDAOMemoria;
import persistencia.memoria.JugadorDAOMemoria;
import persistencia.memoria.UsuarioDAOMemoria;

public class DataController {
    private static UsuarioDAO usuarioDAO = new UsuarioDAOMemoria();
    private static CompeticionDAO competicionDAO = new CompeticionDAOMemoria();
    private static EquipoDAO equipoDAO = new EquipoDAOMemoria();
    private static JugadorDAO jugadorDAO = new JugadorDAOMemoria();

    // Métodos para gestionar usuarios
    public static void agregarUsuario(Usuario usuario) {
        usuarioDAO.agregarUsuario(usuario);
    }
    
    public static List<Usuario> getUsuarios() {
        return usuarioDAO.listarUsuarios();
    }
    
    public static Usuario buscarUsuarioPorEmail(String email) {
        return usuarioDAO.buscarUsuarioPorEmail(email);
    }
    
    public static void eliminarUsuario(String email) {
        usuarioDAO.eliminarUsuario(email);
    }
    
    // Métodos para gestionar competiciones
    public static void agregarCompeticion(Competicion competicion) {
        competicionDAO.agregarCompeticion(competicion);
    }
    
    public static List<Competicion> getCompeticiones() {
        return competicionDAO.listarCompeticiones();
    }
    
    public static Competicion buscarCompeticionPorNombre(String nombre) {
        return competicionDAO.buscarCompeticionPorNombre(nombre);
    }
    
    public static void eliminarCompeticion(String nombre) {
        competicionDAO.eliminarCompeticion(nombre);
    }
    
    // Métodos para gestionar equipos
    public static void agregarEquipo(Equipo equipo) {
        equipoDAO.agregarEquipo(equipo);
    }
    
    public static List<Equipo> getEquipos() {
        return equipoDAO.listarEquipos();
    }
    
    public static Equipo buscarEquipoPorNombre(String nombre) {
        return equipoDAO.buscarEquipoPorNombre(nombre);
    }
    
    public static void eliminarEquipo(String nombre) {
        equipoDAO.eliminarEquipo(nombre);
    }
    
    // Métodos para gestionar jugadores
    public static void agregarJugador(Jugador jugador) {
        jugadorDAO.agregarJugador(jugador);
    }
    
    public static List<Jugador> getJugadores() {
        return jugadorDAO.listarJugadores();
    }
    
    public static Jugador buscarJugadorPorNombre(String nombre) {
        return jugadorDAO.buscarJugadorPorNombre(nombre);
    }
    
    public static void eliminarJugador(String nombre) {
        jugadorDAO.eliminarJugador(nombre);
    }
}
