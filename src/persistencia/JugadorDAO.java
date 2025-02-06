/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;


import java.util.List;
import modelo.Jugador;

public interface JugadorDAO {
    void agregarJugador(Jugador jugador);
    List<Jugador> listarJugadores();
    Jugador buscarJugadorPorNombre(String nombre);
    void eliminarJugador(String nombre);
}