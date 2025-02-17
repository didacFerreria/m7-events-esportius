package vista;

import controlador.DataController;
import modelo.Competicion;
import modelo.Enfrentamiento;
import modelo.Equipo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CompeticionEstadoDialogo extends JDialog {
    private Competicion competicion;
    private DataController dataController;
    private JPanel panelTorneo;
    private JButton btnSimular;
    private boolean rondaFinalizada = false;  // Para controlar el estado de la simulación

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
        panelTorneo.removeAll();

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

        // Crear estructura de paneles para simular la cuadrícula de torneo
        JPanel panelTorneoVista = new JPanel();
        panelTorneoVista.setLayout(new GridLayout(enfrentamientos.size(), 3, 20, 10)); // Coloca enfrentamientos de forma ordenada

        for (Enfrentamiento enfrentamiento : enfrentamientos) {
            JPanel panel1 = crearPanelEquipo(enfrentamiento.getEquipo1());
            JPanel panel2 = crearPanelEquipo(enfrentamiento.getEquipo2());

            JLabel lblVS = new JLabel("VS", SwingConstants.CENTER);
            lblVS.setFont(new Font("Arial", Font.BOLD, 16));

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
            panelTorneoVista.add(lblVS);
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

        // **Determinar color del equipo según resultado**
        if (competicion.getEnfrentamientos() != null) {
            for (Enfrentamiento enfrentamiento : competicion.getEnfrentamientos()) {
                if (enfrentamiento.isFinalizado()) {
                    if (enfrentamiento.getGanador().equals(equipo)) {
                        panelEquipo.setBackground(Color.GREEN);  // **Ganador en verde**
                    } else if (enfrentamiento.getEquipo1().equals(equipo) || enfrentamiento.getEquipo2().equals(equipo)) {
                        panelEquipo.setBackground(Color.RED);  // **Perdedor en rojo**
                    }
                }
            }
        }

        return panelEquipo;
    }

    private void simularPartidos() {
        List<Enfrentamiento> enfrentamientos = competicion.getEnfrentamientos();

        if (!rondaFinalizada) {
            // **Paso 1: Marcar los enfrentamientos como finalizados y actualizar la vista**
            for (Enfrentamiento enfrentamiento : enfrentamientos) {
                enfrentamiento.simular();  // Simular el ganador
            }
            rondaFinalizada = true;
            btnSimular.setText("Siguiente Ronda");  // Cambiar el texto del botón
        } else {
            // **Comprobación: Si solo queda un equipo, el torneo ha terminado**
            if (enfrentamientos.size() == 1) {
                JOptionPane.showMessageDialog(this,
                        "¡El torneo ha finalizado! El ganador es: " + enfrentamientos.get(0).getGanador().getNombre(),
                        "Torneo Finalizado",
                        JOptionPane.INFORMATION_MESSAGE);
                btnSimular.setEnabled(false);  // Deshabilitar el botón para evitar reinicios
                return;  // Salimos del método para no generar nuevas rondas
            }

            // **Paso 2: Avanzar a la siguiente fase**
            List<Enfrentamiento> nuevaRonda = new ArrayList<>();

            for (int i = 0; i < enfrentamientos.size(); i += 2) {
                if (i + 1 < enfrentamientos.size()) {
                    Equipo ganador1 = enfrentamientos.get(i).getGanador();
                    Equipo ganador2 = enfrentamientos.get(i + 1).getGanador();
                    nuevaRonda.add(new Enfrentamiento(ganador1, ganador2));
                }
            }

            // **Actualizar los enfrentamientos de la competición**
            competicion.setEnfrentamientos(nuevaRonda);
            rondaFinalizada = false;
            btnSimular.setText("Simular Partidos");
        }

        generarVistaSegunCompeticion();
    }



}
