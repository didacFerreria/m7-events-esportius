package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import modelo.Equipo;
import modelo.Jugador;

public class EquiposDetalleDialogo extends JDialog {
    private JLabel lblNombreEquipo;
    private JLabel lblCantidadJugadores;
    private JTable tablaJugadores;
    private DefaultTableModel modeloTabla;

    public EquiposDetalleDialogo(JDialog parent, Equipo equipo) {
        super(parent, "Detalles del Equipo", true);
        setLayout(new BorderLayout());
        setSize(500, 400);
        setLocationRelativeTo(parent);

        JPanel panelInfo = new JPanel(new GridLayout(2, 2, 10, 10));
        panelInfo.add(new JLabel("Nombre del Equipo:"));
        lblNombreEquipo = new JLabel(equipo.getNombre());
        panelInfo.add(lblNombreEquipo);

        panelInfo.add(new JLabel("Cantidad de Jugadores:"));
        lblCantidadJugadores = new JLabel(String.valueOf(equipo.getCantidadJugadores()));
        panelInfo.add(lblCantidadJugadores);

        add(panelInfo, BorderLayout.NORTH);

        // Tabla de jugadores
        modeloTabla = new DefaultTableModel(new String[]{
            "Nombre", "Apellido", "Email", "Dorsal", "Posición"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaJugadores = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaJugadores);
        add(scrollPane, BorderLayout.CENTER);

        // Llenar la tabla con los jugadores
        for (Jugador jugador : equipo.getJugadores()) {
            modeloTabla.addRow(new Object[]{jugador.getNombre(), jugador.getApellido(), 
                jugador.getEmail(), jugador.getDorsal(), jugador.getPosicion()});
        }

        setVisible(true);
    }
}
