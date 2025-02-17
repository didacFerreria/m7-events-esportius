package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Competicion {
    private String nombre;
    private String tipoEvento;
    private LocalDate fecha;
    private int numeroEquipos;
    private String categoria;
    private String estado; // Estado de la competición
    private ArrayList<Equipo> equipos;
    private List<Enfrentamiento> enfrentamientos; // Nuevo atributo para los enfrentamientos
    private String ganador;

    public Competicion(String nombre, String tipoEvento, LocalDate fecha, int numeroEquipos, String categoria) {
        this.nombre = nombre;
        this.tipoEvento = tipoEvento;
        this.fecha = fecha;
        this.numeroEquipos = numeroEquipos;
        this.categoria = categoria;
        this.estado = "Pendiente"; // Estado inicial por defecto
        this.equipos = new ArrayList<>();
        this.enfrentamientos = new ArrayList<>();
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipoEvento() { return tipoEvento; }
    public void setTipoEvento(String tipoEvento) { this.tipoEvento = tipoEvento; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public int getNumeroEquipos() { return numeroEquipos; }
    public void setNumeroEquipos(int numeroEquipos) { this.numeroEquipos = numeroEquipos; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public ArrayList<Equipo> getEquipos() { return equipos; }
    public void agregarEquipo(Equipo equipo) {
        if (equipos.size() < numeroEquipos) {
            equipos.add(equipo);
        } else {
            System.out.println("No se pueden agregar más equipos a la competición.");
        }
    }

    public List<Enfrentamiento> getEnfrentamientos() {
        return enfrentamientos;
    }

    public void setEnfrentamientos(List<Enfrentamiento> enfrentamientos) {
        this.enfrentamientos = enfrentamientos;
    }

    public String getGanador() {
        return ganador;
    }

    public boolean esFinalizada() {
        return "Finalizada".equals(estado);
    }

    public void finalizarCompeticion(String ganador) {
        this.ganador = ganador;
        this.estado = "Finalizada";
    }


    @Override
    public String toString() {
        return "Competicion{" +
                "nombre='" + nombre + '\'' +
                ", tipoEvento='" + tipoEvento + '\'' +
                ", fecha=" + fecha +
                ", numeroEquipos=" + numeroEquipos +
                ", categoria='" + categoria + '\'' +
                ", estado='" + estado + '\'' +
                ", equipos=" + equipos +
                ", enfrentamientos=" + enfrentamientos +
                '}';
    }
}
