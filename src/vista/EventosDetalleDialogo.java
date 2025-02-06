package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import modelo.Competicion;
import modelo.Equipo;

public class EventosDetalleDialogo extends JDialog {
    private JLabel lblNombre;
    private JLabel lblTipoEvento;
    private JLabel lblFecha;
    private JLabel lblCategoria;
    private JLabel lblNumeroEquipos;
    private JTable tablaEquipos;
    private DefaultTableModel modeloTabla;

    public EventosDetalleDialogo(JFrame parent, Competicion competicion) {
        super(parent, "Detalles del Evento", true);
        setLayout(new BorderLayout());
        setSize(500, 400);
        setLocationRelativeTo(parent);

        JPanel panelInfo = new JPanel(new GridLayout(5, 2, 10, 10));
        panelInfo.add(new JLabel("Nombre del Evento:"));
        lblNombre = new JLabel(competicion.getNombre());
        panelInfo.add(lblNombre);

        panelInfo.add(new JLabel("Tipo de Evento:"));
        lblTipoEvento = new JLabel(competicion.getTipoEvento());
        panelInfo.add(lblTipoEvento);

        panelInfo.add(new JLabel("Fecha:"));
        lblFecha = new JLabel(competicion.getFecha().toString());
        panelInfo.add(lblFecha);

        panelInfo.add(new JLabel("Categoría:"));
        lblCategoria = new JLabel(competicion.getCategoria());
        panelInfo.add(lblCategoria);

        panelInfo.add(new JLabel("Número de Equipos:"));
        lblNumeroEquipos = new JLabel(String.valueOf(competicion.getNumeroEquipos()));
        panelInfo.add(lblNumeroEquipos);

        add(panelInfo, BorderLayout.NORTH);

        // Tabla de equipos
        modeloTabla = new DefaultTableModel(new String[]{"Nombre del Equipo", "Número de Jugadores"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaEquipos = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaEquipos);
        add(scrollPane, BorderLayout.CENTER);

        // Llenar la tabla con los equipos
        for (Equipo equipo : competicion.getEquipos()) {
            modeloTabla.addRow(new Object[]{equipo.getNombre(), equipo.getCantidadJugadores()});
        }

        // Doble clic en un equipo para ver detalles
        tablaEquipos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int filaSeleccionada = tablaEquipos.getSelectedRow();
                    if (filaSeleccionada != -1) {
                        String nombreEquipo = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
                        for (Equipo equipo : competicion.getEquipos()) {
                            if (equipo.getNombre().equals(nombreEquipo)) {
                                new EquiposDetalleDialogo(EventosDetalleDialogo.this, equipo);
                                break;
                            }
                        }
                    }
                }
            }
        });

        setVisible(true);
    }
}
