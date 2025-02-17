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

        JButton btnFinalizar = new JButton("Finalizar Competición");
        btnFinalizar.setPreferredSize(new Dimension(150, 40));
        btnFinalizar.addActionListener(e -> finalizarCompeticion());

        JPanel panelBoton = new JPanel();
        panelBoton.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panelBoton.add(btnSimular);
        panelBoton.add(btnFinalizar);
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
        } else if ("Cursa de Muntanya".equals(competicion.getTipoEvento()) ||
                "Competició Natació".equals(competicion.getTipoEvento())) {
            generarVistaCarrera();
        } else if ("Campionat de Basquet (Torneig)".equals(competicion.getTipoEvento())) {
            generarVistaTorneo();
        }

        panelTorneo.revalidate();
        panelTorneo.repaint();
    }

    private void generarVistaLiga() {
        panelTorneo.removeAll();

        // Obtener enfrentamientos de la competición o generarlos en la primera ronda
        List<Enfrentamiento> enfrentamientos = competicion.getEnfrentamientos();
        if (enfrentamientos == null || enfrentamientos.isEmpty()) {
            List<Equipo> equipos = new ArrayList<>(competicion.getEquipos());
            competicion.setEnfrentamientos(generarPartidosLiga(equipos)); // Genera enfrentamientos sin cambiar el orden
            enfrentamientos = competicion.getEnfrentamientos();
        }

        JPanel panelMatches = new JPanel(new GridLayout(0, 3, 10, 15));
        panelMatches.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        for (Enfrentamiento enfrentamiento : enfrentamientos) {
            JPanel equipoPanel1 = crearPanelEquipo(enfrentamiento.getEquipo1());
            JLabel lblVS = new JLabel("VS", SwingConstants.CENTER);
            lblVS.setFont(new Font("Arial", Font.BOLD, 16));

            JPanel equipoPanel2 = enfrentamiento.getEquipo2() != null ? crearPanelEquipo(enfrentamiento.getEquipo2()) : new JPanel();

            // **Respetar el orden original en la misma ronda**
            if (enfrentamiento.isFinalizado()) {
                if (enfrentamiento.getGanador().equals(enfrentamiento.getEquipo1())) {
                    equipoPanel1.setBackground(Color.GREEN);
                    equipoPanel2.setBackground(Color.RED);
                } else {
                    equipoPanel1.setBackground(Color.RED);
                    equipoPanel2.setBackground(Color.GREEN);
                }
            }

            panelMatches.add(equipoPanel1);
            panelMatches.add(lblVS);
            panelMatches.add(equipoPanel2);
        }

        panelTorneo.add(panelMatches);
    }

    private void generarVistaCarrera() {
        List<Equipo> equipos = new ArrayList<>(competicion.getEquipos());

        if (rondaFinalizada) {
            Collections.shuffle(equipos); // Seleccionar aleatoriamente los primeros 3 lugares
        }

        JPanel panelCarrera = new JPanel();
        panelCarrera.setLayout(new BoxLayout(panelCarrera, BoxLayout.Y_AXIS));
        panelCarrera.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        for (int i = 0; i < equipos.size(); i++) {
            JPanel equipoPanel = crearPanelEquipo(equipos.get(i));

            if (rondaFinalizada) {
                if (i == 0) equipoPanel.setBackground(Color.GREEN);
                if (i == 1) equipoPanel.setBackground(Color.YELLOW);
                if (i == 2) equipoPanel.setBackground(Color.ORANGE);
            }

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

        JLabel imagen = new JLabel(new ImageIcon(
                getClass().getClassLoader().getResource("resources/equipo-perfil.png")));
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
        // 📌 LIGA: No cambia el orden en la misma ronda
        if ("Campionat de Basquet (Lliga)".equals(competicion.getTipoEvento())) {
            if (!rondaFinalizada) {
                // Marcar los ganadores sin cambiar los enfrentamientos aún
                for (Enfrentamiento enfrentamiento : competicion.getEnfrentamientos()) {
                    enfrentamiento.simular();
                }

                rondaFinalizada = true;
                btnSimular.setText("Siguiente Ronda");
            } else {
                // Generar nueva ronda con enfrentamientos distintos
                competicion.setEnfrentamientos(generarPartidosLiga(new ArrayList<>(competicion.getEquipos())));
                rondaFinalizada = false;
                btnSimular.setText("Simular Partidos");
            }

            generarVistaSegunCompeticion();
            return;
        }

        // 📌 CARRERA/NATACIÓN: No muestra ganadores antes de presionar el botón
        if ("Cursa de Muntanya".equals(competicion.getTipoEvento()) || "Competició Natació".equals(competicion.getTipoEvento())) {
            if (!rondaFinalizada) {
                // Desordenamos y asignamos 3 primeros puestos aleatorios
                List<Equipo> equipos = new ArrayList<>(competicion.getEquipos());
                Collections.shuffle(equipos);
                competicion.setEnfrentamientos(Collections.singletonList(new Enfrentamiento(equipos.get(0), equipos.get(1))));
                rondaFinalizada = true;
                btnSimular.setEnabled(false); // Bloquea simulación tras mostrar resultados
            }

            generarVistaSegunCompeticion();
            return;
        }

        // 📌 TORNEO: Se bloquea al final
        List<Enfrentamiento> enfrentamientos = competicion.getEnfrentamientos();
        if (!rondaFinalizada) {
            for (Enfrentamiento enfrentamiento : enfrentamientos) {
                enfrentamiento.simular();
            }
            rondaFinalizada = true;
            btnSimular.setText("Siguiente Ronda");
        } else {
            if (enfrentamientos.size() == 1) {
                JOptionPane.showMessageDialog(this,
                        "¡El torneo ha finalizado! El ganador es: " + enfrentamientos.get(0).getGanador().getNombre(),
                        "Torneo Finalizado",
                        JOptionPane.INFORMATION_MESSAGE);
                btnSimular.setEnabled(false);
                return;
            }

            List<Enfrentamiento> nuevaRonda = new ArrayList<>();
            for (int i = 0; i < enfrentamientos.size(); i += 2) {
                if (i + 1 < enfrentamientos.size()) {
                    Equipo ganador1 = enfrentamientos.get(i).getGanador();
                    Equipo ganador2 = enfrentamientos.get(i + 1).getGanador();
                    nuevaRonda.add(new Enfrentamiento(ganador1, ganador2));
                }
            }
            competicion.setEnfrentamientos(nuevaRonda);
            rondaFinalizada = false;
            btnSimular.setText("Simular Partidos");
        }

        generarVistaSegunCompeticion();
    }

    private List<Enfrentamiento> generarPartidosLiga(List<Equipo> equipos) {
        List<Enfrentamiento> enfrentamientosLiga = new ArrayList<>();
        Collections.shuffle(equipos);  // Para mezclar los equipos en cada ronda

        // Generamos enfrentamientos por parejas (solo una ronda a la vez)
        for (int i = 0; i < equipos.size(); i += 2) {
            if (i + 1 < equipos.size()) {
                enfrentamientosLiga.add(new Enfrentamiento(equipos.get(i), equipos.get(i + 1)));
            }
        }

        return enfrentamientosLiga;
    }

    private void finalizarCompeticion() {
        if ("Campionat de Basquet (Torneig)".equals(competicion.getTipoEvento())) {
            // 🏆 Si es torneo, el ganador es el último en pie
            if (!competicion.getEnfrentamientos().isEmpty()) {
                Enfrentamiento finalMatch = competicion.getEnfrentamientos().get(competicion.getEnfrentamientos().size() - 1);
                if (finalMatch.isFinalizado()) {
                    competicion.finalizarCompeticion(String.valueOf(finalMatch.getGanador()));
                }
            }
        } else if ("Cursa de Muntanya".equals(competicion.getTipoEvento()) ||
                "Competició Natació".equals(competicion.getTipoEvento())) {
            // 🏁 Si es carrera/natación, el ganador es el primero en la lista final
            if (!competicion.getEquipos().isEmpty()) {
                competicion.finalizarCompeticion(competicion.getEquipos().get(0).getNombre()); // El primero es el ganador
            }
        }

        competicion.setEstado("Finalizada");
        JOptionPane.showMessageDialog(this,
                "La competición ha sido finalizada. Resultados guardados.",
                "Competición Finalizada", JOptionPane.INFORMATION_MESSAGE);
        btnSimular.setEnabled(false);
    }



}
