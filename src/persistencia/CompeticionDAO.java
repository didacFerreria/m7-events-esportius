/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.util.List;
import modelo.Competicion;

public interface CompeticionDAO {
    void agregarCompeticion(Competicion competicion);
    List<Competicion> listarCompeticiones();
    Competicion buscarCompeticionPorNombre(String nombre);
    void eliminarCompeticion(String nombre);
}
