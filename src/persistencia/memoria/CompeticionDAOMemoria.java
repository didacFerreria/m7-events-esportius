/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia.memoria;

import java.util.ArrayList;
import java.util.List;
import modelo.Competicion;
import persistencia.CompeticionDAO;

public class CompeticionDAOMemoria implements CompeticionDAO {
    private List<Competicion> competiciones = new ArrayList<>();

    @Override
    public void agregarCompeticion(Competicion competicion) {
        competiciones.add(competicion);
    }

    @Override
    public List<Competicion> listarCompeticiones() {
        return new ArrayList<>(competiciones);
    }

    @Override
    public Competicion buscarCompeticionPorNombre(String nombre) {
        return competiciones.stream()
                .filter(c -> c.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void eliminarCompeticion(String nombre) {
        competiciones.removeIf(c -> c.getNombre().equalsIgnoreCase(nombre));
    }
}

