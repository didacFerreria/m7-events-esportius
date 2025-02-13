package vista;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import controlador.DataController;
import modelo.Competicion;
import modelo.Equipo;

public class CompeticionEstadoDialogo extends JDialog {
    private Competicion competicion;
    private DataController dataController;
    private JPanel panelTorneo;
    private JButton btnSimular;

    public CompeticionEstadoDialogo(JFrame parent, Competicion competicion, DataController dataController) {
        super(parent, "Estado de la Competición", true);
        this.competicion = competicion;
        this.dataController = dataController;

        setLayout(new BorderLayout());
        setSize(800, 600);
        setLocationRelativeTo(parent);

        JLabel lblTitulo = new JLabel("Estado de la Competición: " + competicion.getNombre(), SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblTitulo, BorderLayout.NORTH);

        panelTorneo = new JPanel();
        panelTorneo.setLayout(new GridLayout(0, 1, 10, 10));
        JScrollPane scrollPane = new JScrollPane(panelTorneo, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        btnSimular = new JButton("Simular Partidos");
        btnSimular.addActionListener(e -> simularPartidos());
        add(btnSimular, BorderLayout.SOUTH);

        generarCuadroTorneo();
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void generarCuadroTorneo() {
        panelTorneo.removeAll();
        List<Equipo> equipos = new ArrayList<>(competicion.getEquipos());
        Collections.shuffle(equipos);

        JPanel panelMatches = new JPanel();
        panelMatches.setLayout(new GridLayout(equipos.size() / 2, 3, 10, 10));

        for (int i = 0; i < equipos.size(); i += 2) {
            JPanel equipoPanel1 = crearPanelEquipo(equipos.get(i));
            panelMatches.add(equipoPanel1);

            JLabel lblVS = new JLabel("VS", SwingConstants.CENTER);
            lblVS.setFont(new Font("Arial", Font.BOLD, 18));
            panelMatches.add(lblVS);

            if (i + 1 < equipos.size()) {
                JPanel equipoPanel2 = crearPanelEquipo(equipos.get(i + 1));
                panelMatches.add(equipoPanel2);
            } else {
                panelMatches.add(new JLabel("BYE", SwingConstants.CENTER));
            }
        }

        panelTorneo.add(panelMatches);
        panelTorneo.revalidate();
        panelTorneo.repaint();
    }

    private JPanel crearPanelEquipo(Equipo equipo) {
        JPanel panelEquipo = new JPanel(new BorderLayout());
        panelEquipo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelEquipo.setPreferredSize(new Dimension(200, 50));

        JLabel imagen = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("resources/equipo-perfil.png")));
        panelEquipo.add(imagen, BorderLayout.WEST);

        JPanel datosEquipo = new JPanel(new GridLayout(2, 1));
        datosEquipo.add(new JLabel("Nombre: " + equipo.getNombre(), SwingConstants.LEFT));
        datosEquipo.add(new JLabel("Jugadores: " + equipo.getCantidadJugadores(), SwingConstants.LEFT));

        panelEquipo.add(datosEquipo, BorderLayout.CENTER);

        return panelEquipo;
    }

    private void simularPartidos() {
        JOptionPane.showMessageDialog(this, "Simulación de partidos en desarrollo...");
        // Aquí se implementará la lógica de simulación de partidos en el futuro
    }
}
