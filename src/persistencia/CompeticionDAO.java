package persistencia;

import java.util.List;
import modelo.Competicion;
import modelo.Enfrentamiento;

public interface CompeticionDAO {
    void agregarCompeticion(Competicion competicion);
    List<Competicion> listarCompeticiones();
    Competicion buscarCompeticionPorNombre(String nombre);
    void eliminarCompeticion(String nombre);
    List<Competicion> listarCompeticionesFinalizadas();
    void guardarEnfrentamientos(String nombreCompeticion, List<Enfrentamiento> enfrentamientos);
    List<Enfrentamiento> obtenerEnfrentamientos(String nombreCompeticion);
}
