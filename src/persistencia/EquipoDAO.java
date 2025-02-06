/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.util.List;
import modelo.Equipo;

public interface EquipoDAO {
    void agregarEquipo(Equipo equipo);
    List<Equipo> listarEquipos();
    Equipo buscarEquipoPorNombre(String nombre);
    void eliminarEquipo(String nombre);
}

