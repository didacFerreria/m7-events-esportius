package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import modelo.Equipo;
import modelo.Jugador;

public class EquiposAgregarDialogo extends JDialog {
    private JTextField txtNombreEquipo;
    private JSpinner spnCantidadJugadores;
    private JTable tablaJugadores;
    private DefaultTableModel modeloTabla;
    private JButton btnGuardar;
    private List<Jugador> jugadores;
    private Equipo equipo;

    public EquiposAgregarDialogo(JDialog parent, String nombreEquipo, int cantidadJugadores) {
        super(parent, "Agregar Equipo", true);
        this.jugadores = new ArrayList<>();
        this.equipo = new Equipo(nombreEquipo, cantidadJugadores);

        setLayout(new BorderLayout());
        setSize(600, 400);
        setLocationRelativeTo(parent);

        JPanel panelSuperior = new JPanel(new GridLayout(2, 2, 10, 10));
        panelSuperior.add(new JLabel("Nombre del equipo:"));
        txtNombreEquipo = new JTextField(nombreEquipo);
        txtNombreEquipo.setEditable(false);
        panelSuperior.add(txtNombreEquipo);

        panelSuperior.add(new JLabel("Cantidad de jugadores:"));
        spnCantidadJugadores = new JSpinner(new SpinnerNumberModel(cantidadJugadores, cantidadJugadores, 50, 1));
        spnCantidadJugadores.addChangeListener(e -> actualizarTablaJugadores());
        panelSuperior.add(spnCantidadJugadores);

        add(panelSuperior, BorderLayout.NORTH);

        // Tabla de jugadores con más columnas
        modeloTabla = new DefaultTableModel(new String[]{
                "Nombre", "Apellido", "Email", "Dorsal", "Posición"}, 0);
        tablaJugadores = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaJugadores);
        add(scrollPane, BorderLayout.CENTER);

        // Botón para guardar
        btnGuardar = new JButton("Guardar Equipo");
        btnGuardar.addActionListener(e -> guardarEquipo());
        add(btnGuardar, BorderLayout.SOUTH);

        actualizarTablaJugadores();
        setVisible(true);
    }

    public Equipo getEquipo() {
        return equipo;
    }
    
    private void actualizarTablaJugadores() {
        int cantidad = (int) spnCantidadJugadores.getValue();
        modeloTabla.setRowCount(0);
        for (int i = 0; i < cantidad; i++) {
            modeloTabla.addRow(new Object[]{
                    "Jugador " + (i + 1), "Apellido " + (i + 1), "email" + (i + 1) +
                    "@example.com", i + 1, "Posición"});
        }
    }

    private void guardarEquipo() {
        String nombre = txtNombreEquipo.getText();
        int cantidad = (int) spnCantidadJugadores.getValue();
        
        jugadores.clear();
        for (int i = 0; i < cantidad; i++) {
            String nombreJugador = (String) modeloTabla.getValueAt(i, 0);
            String apellidoJugador = (String) modeloTabla.getValueAt(i, 1);
            String emailJugador = (String) modeloTabla.getValueAt(i, 2);
            int dorsal = (int) modeloTabla.getValueAt(i, 3);
            String posicion = (String) modeloTabla.getValueAt(i, 4);
            jugadores.add(new Jugador(nombreJugador, apellidoJugador, 
                    emailJugador, dorsal, posicion));
        }
        
        equipo.setJugadores(jugadores);
        JOptionPane.showMessageDialog(this, "Equipo guardado exitosamente.");
        dispose();
    }
}
