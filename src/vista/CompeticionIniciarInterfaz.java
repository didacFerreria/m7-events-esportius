package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import controlador.DataController;
import modelo.Competicion;

public class CompeticionIniciarInterfaz extends JFrame {
    private JTable tablaPendientes;
    private JTable tablaEnCurso;
    private DefaultTableModel modeloPendientes;
    private DefaultTableModel modeloEnCurso;
    private JButton btnIniciar;
    private JButton btnAvanzar;
    private DataController dataController;

    public CompeticionIniciarInterfaz(DataController dataController) {
        this.dataController = dataController;

        setTitle("Gestionar Competiciones");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Pestaña de competiciones pendientes
        JPanel panelPendientes = new JPanel(new BorderLayout());
        modeloPendientes = new DefaultTableModel(new String[]{"Nombre", "Tipo de Evento", "Fecha", "Número de Equipos", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaPendientes = new JTable(modeloPendientes);
        panelPendientes.add(new JScrollPane(tablaPendientes), BorderLayout.CENTER);

        btnIniciar = new JButton("Iniciar Competición");
        btnIniciar.addActionListener(this::iniciarCompeticion);
        panelPendientes.add(btnIniciar, BorderLayout.SOUTH);

        tabbedPane.addTab("Pendientes", panelPendientes);

        // Pestaña de competiciones en curso
        JPanel panelEnCurso = new JPanel(new BorderLayout());
        modeloEnCurso = new DefaultTableModel(new String[]{
                "Nombre", "Tipo de Evento", "Fecha", "Número de Equipos", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaEnCurso = new JTable(modeloEnCurso);
        panelEnCurso.add(new JScrollPane(tablaEnCurso), BorderLayout.CENTER);

        btnAvanzar = new JButton("Avanzar/Finalizar Competición");
        panelEnCurso.add(btnAvanzar, BorderLayout.SOUTH);

        tabbedPane.addTab("En Curso", panelEnCurso);

        add(tabbedPane, BorderLayout.CENTER);
        cargarCompeticiones();

        // Doble clic en competiciones pendientes para ver detalles
        tablaPendientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int filaSeleccionada = tablaPendientes.getSelectedRow();
                    if (filaSeleccionada != -1) {
                        String nombreCompeticion = (String) modeloPendientes.getValueAt(filaSeleccionada, 0);
                        Competicion competicion = dataController.buscarCompeticionPorNombre(nombreCompeticion);
                        if (competicion != null) {
                            new EventosDetalleDialogo(CompeticionIniciarInterfaz.this, competicion);
                        }
                    }
                }
            }
        });

        btnAvanzar.addActionListener(e -> {
            int filaSeleccionada = tablaEnCurso.getSelectedRow();
            if (filaSeleccionada != -1) {
                String nombreCompeticion = (String) modeloEnCurso.getValueAt(filaSeleccionada, 0);
                Competicion competicion = dataController.buscarCompeticionPorNombre(nombreCompeticion);
                if (competicion != null) {
                    new CompeticionEstadoDialogo(CompeticionIniciarInterfaz.this, competicion, dataController);
                }
            }
        });

        setVisible(true);
    }

    private void cargarCompeticiones() {
        modeloPendientes.setRowCount(0);
        modeloEnCurso.setRowCount(0);
        List<Competicion> competiciones = dataController.getCompeticiones();
        for (Competicion competicion : competiciones) {
            if ("Pendiente".equals(competicion.getEstado())) {
                modeloPendientes.addRow(new Object[]{
                        competicion.getNombre(), competicion.getTipoEvento(), competicion.getFecha(), competicion.getNumeroEquipos(), competicion.getEstado()
                });
            } else if ("En curso".equals(competicion.getEstado())) {
                modeloEnCurso.addRow(new Object[]{
                        competicion.getNombre(), competicion.getTipoEvento(), competicion.getFecha(), competicion.getNumeroEquipos(), competicion.getEstado()
                });
            }
        }
    }

    private void iniciarCompeticion(ActionEvent e) {
        int filaSeleccionada = tablaPendientes.getSelectedRow();
        if (filaSeleccionada != -1) {
            String nombreCompeticion = (String) modeloPendientes.getValueAt(filaSeleccionada, 0);
            Competicion competicion = dataController.buscarCompeticionPorNombre(nombreCompeticion);
            if (competicion != null) {
                competicion.setEstado("En curso");
                JOptionPane.showMessageDialog(this, "La competición ha sido iniciada.");
                cargarCompeticiones();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una competición para iniciar.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
