package vista;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import controlador.DataController;
import modelo.Competicion;
import modelo.Enfrentamiento;
import modelo.Equipo;

public class CompeticionEstadoDialogo extends JDialog {
    private Competicion competicion;
    private DataController dataController;
    private JPanel panelTorneo;
    private JButton btnSimular;

    public CompeticionEstadoDialogo(JFrame parent, Competicion competicion, DataController dataController) {
        super(parent, "Estado de la competición " + competicion.getNombre(), true);
        this.competicion = competicion;
        this.dataController = dataController;

        setLayout(new BorderLayout());
        setSize(900, 600);
        setLocationRelativeTo(parent);

        JLabel lblTitulo = new JLabel("Estado de la competición " + competicion.getNombre(), SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(lblTitulo, BorderLayout.NORTH);

        panelTorneo = new JPanel();
        panelTorneo.setLayout(new BoxLayout(panelTorneo, BoxLayout.Y_AXIS));
        panelTorneo.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JScrollPane scrollPane = new JScrollPane(panelTorneo, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        btnSimular = new JButton("Simular Partidos");
        btnSimular.setPreferredSize(new Dimension(150, 40));
        btnSimular.addActionListener(e -> simularPartidos());

        JPanel panelBoton = new JPanel();
        panelBoton.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panelBoton.add(btnSimular);
        add(panelBoton, BorderLayout.SOUTH);

        // Generar vista según el tipo de competición
        generarVistaSegunCompeticion();
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void generarVistaSegunCompeticion() {
        panelTorneo.removeAll();

        if ("Campionat de Basquet (Lliga)".equals(competicion.getTipoEvento())) {
            generarVistaLiga();
        } else if ("Cursa de Muntanya".equals(competicion.getTipoEvento())) {
            generarVistaCarrera();
        } else if ("Campionat de Basquet (Torneig)".equals(competicion.getTipoEvento())) {
            generarVistaTorneo();
        }

        panelTorneo.revalidate();
        panelTorneo.repaint();
    }

    private void generarVistaLiga() {
        List<Enfrentamiento> enfrentamientos = competicion.getEnfrentamientos();

        if (enfrentamientos == null || enfrentamientos.isEmpty()) {
            List<Equipo> equipos = new ArrayList<>(competicion.getEquipos());
            Collections.shuffle(equipos);

            enfrentamientos = new ArrayList<>();
            for (int i = 0; i < equipos.size(); i += 2) {
                if (i + 1 < equipos.size()) {
                    enfrentamientos.add(new Enfrentamiento(equipos.get(i), equipos.get(i + 1)));
                }
            }
            competicion.setEnfrentamientos(enfrentamientos);
        }

        JPanel panelMatches = new JPanel();
        panelMatches.setLayout(new GridLayout(0, 3, 10, 15));
        panelMatches.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        for (Enfrentamiento enfrentamiento : enfrentamientos) {
            JPanel equipoPanel1 = crearPanelEquipo(enfrentamiento.getEquipo1());
            panelMatches.add(equipoPanel1);

            JLabel lblVS = new JLabel("VS", SwingConstants.CENTER);
            lblVS.setFont(new Font("Arial", Font.BOLD, 16));
            lblVS.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
            panelMatches.add(lblVS);

            if (enfrentamiento.getEquipo2() != null) {
                JPanel equipoPanel2 = crearPanelEquipo(enfrentamiento.getEquipo2());
                panelMatches.add(equipoPanel2);
            } else {
                panelMatches.add(new JPanel());
            }
        }

        panelTorneo.add(panelMatches);
    }

    private void generarVistaCarrera() {
        JPanel panelCarrera = new JPanel();
        panelCarrera.setLayout(new BoxLayout(panelCarrera, BoxLayout.Y_AXIS));
        panelCarrera.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        for (Equipo equipo : competicion.getEquipos()) {
            JPanel equipoPanel = crearPanelEquipo(equipo);
            panelCarrera.add(equipoPanel);
            panelCarrera.add(Box.createVerticalStrut(10));
        }

        panelTorneo.add(panelCarrera);
    }

    private void generarVistaTorneo() {
        List<Enfrentamiento> enfrentamientos = competicion.getEnfrentamientos();

        if (enfrentamientos == null || enfrentamientos.isEmpty()) {
            List<Equipo> equipos = new ArrayList<>(competicion.getEquipos());
            Collections.shuffle(equipos);

            enfrentamientos = new ArrayList<>();
            for (int i = 0; i < equipos.size(); i += 2) {
                if (i + 1 < equipos.size()) {
                    enfrentamientos.add(new Enfrentamiento(equipos.get(i), equipos.get(i + 1)));
                }
            }
            competicion.setEnfrentamientos(enfrentamientos);
        }

        JPanel panelTorneoVista = new JPanel();
        panelTorneoVista.setLayout(new GridLayout(enfrentamientos.size(), 2, 10, 10));

        for (Enfrentamiento enfrentamiento : enfrentamientos) {
            JPanel panel1 = crearPanelEquipo(enfrentamiento.getEquipo1());
            JPanel panel2 = crearPanelEquipo(enfrentamiento.getEquipo2());

            if (enfrentamiento.isFinalizado()) {
                if (enfrentamiento.getGanador().equals(enfrentamiento.getEquipo1())) {
                    panel1.setBackground(Color.GREEN);
                    panel2.setBackground(Color.RED);
                } else {
                    panel1.setBackground(Color.RED);
                    panel2.setBackground(Color.GREEN);
                }
            }

            panelTorneoVista.add(panel1);
            panelTorneoVista.add(panel2);
        }

        panelTorneo.add(panelTorneoVista);
    }

    private JPanel crearPanelEquipo(Equipo equipo) {
        JPanel panelEquipo = new JPanel(new BorderLayout());
        panelEquipo.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        panelEquipo.setPreferredSize(new Dimension(260, 80));

        JLabel imagen = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("resources/equipo-perfil.png")));
        imagen.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 15));
        panelEquipo.add(imagen, BorderLayout.WEST);

        JPanel datosEquipo = new JPanel();
        datosEquipo.setLayout(new BoxLayout(datosEquipo, BoxLayout.Y_AXIS));
        datosEquipo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblNombre = new JLabel(equipo.getNombre());
        lblNombre.setFont(new Font("Arial", Font.BOLD, 16));
        lblNombre.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblJugadores = new JLabel("Jugadores: " + equipo.getCantidadJugadores());
        lblJugadores.setFont(new Font("Arial", Font.PLAIN, 13));
        lblJugadores.setAlignmentX(Component.LEFT_ALIGNMENT);

        datosEquipo.add(lblNombre);
        datosEquipo.add(Box.createVerticalStrut(5));
        datosEquipo.add(lblJugadores);

        panelEquipo.add(datosEquipo, BorderLayout.CENTER);
        return panelEquipo;
    }

    private void simularPartidos() {
        for (Enfrentamiento enfrentamiento : competicion.getEnfrentamientos()) {
            enfrentamiento.simular();
        }
        generarVistaSegunCompeticion();
    }
}
