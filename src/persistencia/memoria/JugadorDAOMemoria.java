/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia.memoria;

import java.util.ArrayList;
import java.util.List;
import modelo.Jugador;
import persistencia.JugadorDAO;

public class JugadorDAOMemoria implements JugadorDAO {
    private List<Jugador> jugadores = new ArrayList<>();

    @Override
    public void agregarJugador(Jugador jugador) {
        jugadores.add(jugador);
    }

    @Override
    public List<Jugador> listarJugadores() {
        return new ArrayList<>(jugadores);
    }

    @Override
    public Jugador buscarJugadorPorNombre(String nombre) {
        return jugadores.stream()
                .filter(j -> j.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void eliminarJugador(String nombre) {
        jugadores.removeIf(j -> j.getNombre().equalsIgnoreCase(nombre));
    }
}
