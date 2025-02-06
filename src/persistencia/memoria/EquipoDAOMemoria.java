/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia.memoria;

import java.util.ArrayList;
import java.util.List;
import modelo.Equipo;
import persistencia.EquipoDAO;

public class EquipoDAOMemoria implements EquipoDAO {
    private List<Equipo> equipos = new ArrayList<>();

    @Override
    public void agregarEquipo(Equipo equipo) {
        equipos.add(equipo);
    }

    @Override
    public List<Equipo> listarEquipos() {
        return new ArrayList<>(equipos);
    }

    @Override
    public Equipo buscarEquipoPorNombre(String nombre) {
        return equipos.stream()
                .filter(e -> e.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void eliminarEquipo(String nombre) {
        equipos.removeIf(e -> e.getNombre().equalsIgnoreCase(nombre));
    }
}

