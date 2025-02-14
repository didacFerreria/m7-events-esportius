package persistencia.memoria;

import java.util.ArrayList;
import java.util.List;
import modelo.Competicion;
import modelo.Enfrentamiento;
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

    // Métodos para gestionar enfrentamientos
    @Override
    public void guardarEnfrentamientos(String nombreCompeticion, List<Enfrentamiento> enfrentamientos) {
        Competicion competicion = buscarCompeticionPorNombre(nombreCompeticion);
        if (competicion != null) {
            competicion.setEnfrentamientos(enfrentamientos);
        }
    }

    @Override
    public List<Enfrentamiento> obtenerEnfrentamientos(String nombreCompeticion) {
        Competicion competicion = buscarCompeticionPorNombre(nombreCompeticion);
        return (competicion != null) ? competicion.getEnfrentamientos() : new ArrayList<>();
    }
}
