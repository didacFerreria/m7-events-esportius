package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import controlador.DataController;
import modelo.Competicion;
import modelo.Enfrentamiento;

import java.util.List;

public class CompeticionResultadosInterfaz extends JFrame {
    private DataController dataController;
    private JTable tablaResultados;
    private DefaultTableModel modeloTabla;
    private JButton btnVerDetalles;

    public CompeticionResultadosInterfaz(DataController dataController) {
        this.dataController = dataController;

        setTitle("Resultados de Competiciones");
        setSize(800, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Modelo de tabla no editable
        modeloTabla = new DefaultTableModel(new Object[]{"Nombre", "Tipo", "Fecha", "Ganador"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hace que la tabla no sea editable
            }
        };
        tablaResultados = new JTable(modeloTabla);
        cargarResultados();

        // Agregar scroll a la tabla
        JScrollPane scrollPane = new JScrollPane(tablaResultados);
        add(scrollPane, BorderLayout.CENTER);

        // Botón para ver detalles
        btnVerDetalles = new JButton("Ver Detalles");
        btnVerDetalles.setEnabled(false);
        btnVerDetalles.addActionListener(e -> verDetallesCompeticion());
        add(btnVerDetalles, BorderLayout.SOUTH);

        // Evento doble clic en la tabla
        tablaResultados.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 2 && tablaResultados.getSelectedRow() != -1) {
                    verDetallesCompeticion();
                }
            }

            public void mouseReleased(MouseEvent e) {
                btnVerDetalles.setEnabled(tablaResultados.getSelectedRow() != -1);
            }
        });

        setVisible(true);
    }

    private void cargarResultados() {
        modeloTabla.setRowCount(0); // Limpiar tabla
        List<Competicion> competicionesFinalizadas = dataController.obtenerCompeticionesFinalizadas();

        for (Competicion competicion : competicionesFinalizadas) {
            String ganador = obtenerGanadorCompeticion(competicion);
            modeloTabla.addRow(new Object[]{
                    competicion.getNombre(),
                    competicion.getTipoEvento(),
                    competicion.getFecha(),
                    ganador
            });
        }
    }

    // Método auxiliar para obtener el equipo ganador
    private String obtenerGanadorCompeticion(Competicion competicion) {
        if(competicion.getGanador() != null) {
            return competicion.getGanador();
        } else if (competicion.getEnfrentamientos() != null && !competicion.getEnfrentamientos().isEmpty()) {
            Enfrentamiento ultimoEnfrentamiento =
                    competicion.getEnfrentamientos().get(competicion.getEnfrentamientos().size() - 1);

            if (ultimoEnfrentamiento.isFinalizado() && ultimoEnfrentamiento.getGanador() != null) {
                return ultimoEnfrentamiento.getGanador().getNombre();
            }
        }
        return "-"; // Si no hay ganador, mostrar "-"
    }

    private void verDetallesCompeticion() {
        int filaSeleccionada = tablaResultados.getSelectedRow();
        if (filaSeleccionada != -1) {
            String nombreCompeticion = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
            Competicion competicion = dataController.buscarCompeticionPorNombre(nombreCompeticion);
            if (competicion != null) {
                new EventosDetalleDialogo(this, competicion);
            }
        }
    }
}
