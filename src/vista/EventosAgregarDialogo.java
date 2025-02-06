package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import modelo.Competicion;
import persistencia.CompeticionDAO;
import modelo.Equipo;

public class EventosAgregarDialogo extends JDialog {
    private JTextField txtNombre;
    private JComboBox<String> cmbTipoEvento;
    private JLabel lblNumeroEquipos;
    private JComboBox<String> cmbCategoria;
    private JButton btnGuardar;
    private CompeticionDAO competicionDAO;
    private DefaultTableModel modeloEquipos;
    private JTable tablaEquipos;
    private List<Equipo> equipos;

    public EventosAgregarDialogo(JFrame parent, CompeticionDAO competicionDAO) {
        super(parent, "Agregar Evento", true);
        this.competicionDAO = competicionDAO;
        this.equipos = new ArrayList<>();

        setLayout(new BorderLayout());
        setSize(500, 400);
        setLocationRelativeTo(parent);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Pestaña de datos de competición
        JPanel panelDatos = new JPanel(new GridLayout(4, 2, 10, 10));
        panelDatos.add(new JLabel("Nombre del evento:"));
        txtNombre = new JTextField();
        panelDatos.add(txtNombre);
        
        panelDatos.add(new JLabel("Tipo de Evento:"));
        cmbTipoEvento = new JComboBox<>(new String[]{"Campionat de Basquet", "Cursa de Muntanya", "Competició Natació"});
        panelDatos.add(cmbTipoEvento);

        panelDatos.add(new JLabel("Número de equipos:"));
        lblNumeroEquipos = new JLabel("0");
        panelDatos.add(lblNumeroEquipos);

        panelDatos.add(new JLabel("Categoría:"));
        cmbCategoria = new JComboBox<>(new String[]{"Mini", "Preinfantil", "Infantil", "Cadet", "Junior", "Senior"});
        panelDatos.add(cmbCategoria);

        tabbedPane.addTab("Datos Evento", panelDatos);

        // Pestaña de equipos
        JPanel panelEquipos = new JPanel(new BorderLayout());
        modeloEquipos = new DefaultTableModel(new String[]{"Nombre Equipo"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaEquipos = new JTable(modeloEquipos);
        panelEquipos.add(new JScrollPane(tablaEquipos), BorderLayout.CENTER);
        
        JButton btnAgregarEquipo = new JButton("Agregar Equipo");
        btnAgregarEquipo.addActionListener(e -> agregarEquipo());
        panelEquipos.add(btnAgregarEquipo, BorderLayout.SOUTH);
        
        tabbedPane.addTab("Equipos", panelEquipos);

        add(tabbedPane, BorderLayout.CENTER);

        btnGuardar = new JButton("Guardar Evento");
        btnGuardar.addActionListener(e -> guardarEvento());
        add(btnGuardar, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void agregarEquipo() {
        String nombreEquipo = JOptionPane.showInputDialog(this, "Nombre del equipo:");
        if (nombreEquipo != null && !nombreEquipo.trim().isEmpty()) {
            int cantidadJugadores = Integer.parseInt(JOptionPane.showInputDialog(this, "Número de jugadores:"));
            EquiposAgregarDialogo dialogoEquipo = new EquiposAgregarDialogo(this, nombreEquipo, cantidadJugadores);
            equipos.add(dialogoEquipo.getEquipo());
            modeloEquipos.addRow(new Object[]{nombreEquipo});
            lblNumeroEquipos.setText(String.valueOf(equipos.size()));
        }
    }

    private void guardarEvento() {
        String nombre = txtNombre.getText();
        String tipoEvento = (String) cmbTipoEvento.getSelectedItem();
        String categoria = (String) cmbCategoria.getSelectedItem();

        if (nombre.isEmpty() || equipos.size() < 2) {
            JOptionPane.showMessageDialog(this, "El evento debe tener un nombre y al menos 2 equipos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Competicion nuevaCompeticion = new Competicion(nombre, tipoEvento, LocalDate.now(), equipos.size(), categoria);
        for (Equipo equipo : equipos) {
            nuevaCompeticion.agregarEquipo(equipo);
        }

        competicionDAO.agregarCompeticion(nuevaCompeticion);
        JOptionPane.showMessageDialog(this, "Evento agregado exitosamente.");
        dispose();
    }
}