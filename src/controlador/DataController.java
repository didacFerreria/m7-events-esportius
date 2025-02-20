package controlador;

import java.time.LocalDate;
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

    // Método de inicialización automática
    public static void inicializarDatos() {
        // Agregar usuarios por defecto
        crearUsuariosPorDefecto();
        // Agregar competiciones por defecto
        if (competicionDAO.listarCompeticiones().isEmpty()) {
            crearCompeticionPorDefecto();
        }
    }

    private static void crearUsuariosPorDefecto() {
        Usuario usuario = new Usuario(
                "admin", "1", "administrador@gmail.com",
                "1234","Administrador");
        usuarioDAO.agregarUsuario(usuario);
    }

    private static void crearCompeticionPorDefecto() {
        Competicion competicion = new Competicion("Liga Inicial",
                "Campionat de Basquet (Lliga)", LocalDate.now(), // Campionat de Basquet (Torneig/Lliga) / Competició Natació
                10, "Senior");
        //competicion.finalizarCompeticion("E1");

        for (int i = 1; i <= 10; i++) {
            Equipo equipo = new Equipo("E" + i, 20); // Jugadores máximos 20
            for (int j = 1; j <= 5 + (int)(Math.random() * 19); j++) {
                equipo.agregarJugador(new Jugador(
                        "Jugador " + j, "Apellido " + j,
                        "email" + j + "@example.com", j, "Posición"));
            }
            equipoDAO.agregarEquipo(equipo);
            competicion.agregarEquipo(equipo);
        }

        competicionDAO.agregarCompeticion(competicion);
    }

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

    public Usuario buscarUsuario(String nombre) {
        return usuarioDAO.buscarUsuario(nombre);
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

    public List<Competicion> obtenerCompeticionesFinalizadas() {
        return competicionDAO.listarCompeticionesFinalizadas();
    }

}
