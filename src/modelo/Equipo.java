/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;
import java.util.List;

public class Equipo {
    private String nombre;
    private ArrayList<Jugador> jugadores;
    private int maxJugadores;

    public Equipo(String nombre, int maxJugadores) {
        this.nombre = nombre;
        this.maxJugadores = maxJugadores;
        this.jugadores = new ArrayList<>();
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public ArrayList<Jugador> getJugadores() { return jugadores; }
    public int getMaxJugadores() { return maxJugadores; }

    // Método para agregar jugadores
    public boolean agregarJugador(Jugador jugador) {
        if (jugadores.size() < maxJugadores) {
            jugadores.add(jugador);
            return true;
        } else {
            System.out.println("El equipo ya tiene el número máximo de jugadores.");
            return false;
        }
    }

    @Override
    public String toString() {
        return "Equipo{" +
                "nombre='" + nombre + '\'' +
                ", jugadores=" + jugadores +
                ", maxJugadores=" + maxJugadores +
                '}';
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = (ArrayList<Jugador>) jugadores;
    }

    public Object getCantidadJugadores() {
        
        return jugadores.size();
        
    }
}

