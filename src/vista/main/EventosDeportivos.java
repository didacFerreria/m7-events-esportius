package vista.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import controlador.DataController;
import vista.EventosListaInterfaz;
import vista.UsuariosListaInterfaz;
import vista.CompeticionIniciarInterfaz;

public class EventosDeportivos extends JFrame {

    private DataController dataController;
    private JButton btnEventos, btnUsuarios, btnCompeticionIniciar, btnCompeticionResultado;

    public EventosDeportivos() {
        // Inicializar DataController (gestiona todos los DAOs)
        dataController = new DataController();

        setTitle("Aplicació Registre Usuaris");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                int confirm = JOptionPane.showConfirmDialog(EventosDeportivos.this, "¿Estás seguro de que quieres salir?", "Confirmar salida", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Crear el menú superior
        JMenuBar menuBar = new JMenuBar();

        JMenu mestresMenu = new JMenu("Maestros");
        JMenuItem esdevenimentsMenuItem = new JMenuItem("Eventos");
        JMenuItem usuarisMenuItem = new JMenuItem("Usuarios");

        // Conectar con las interfaces de usuarios y eventos
        usuarisMenuItem.addActionListener(e -> new UsuariosListaInterfaz(dataController));
        esdevenimentsMenuItem.addActionListener(e -> new EventosListaInterfaz(dataController));

        mestresMenu.add(esdevenimentsMenuItem);
        mestresMenu.add(usuarisMenuItem);

        JMenu competicioMenu = new JMenu("Competición");
        JMenuItem iniciMenuItem = new JMenuItem("Iniciar");
        JMenuItem resultatsMenuItem = new JMenuItem("Resultados");

        // Conectar con CompeticionIniciarInterfaz
        iniciMenuItem.addActionListener(e -> new CompeticionIniciarInterfaz(dataController));

        competicioMenu.add(iniciMenuItem);
        competicioMenu.add(resultatsMenuItem);

        JMenu ajudaMenu = new JMenu("Ayuda");
        ajudaMenu.add(new JMenuItem("Buscar"));

        JMenu empresaMenu = new JMenu("La empresa");
        JMenuItem aboutItem = new JMenuItem("About");
        JMenu sortirMenu = new JMenu("Configuración");
        JMenuItem sortirItem = new JMenuItem("Salir");
        sortirItem.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(EventosDeportivos.this,
                    "¿Estás seguro de que quieres salir?", "Confirmar salida",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        empresaMenu.add(aboutItem);
        sortirMenu.add(sortirItem);

        // Añadir menús al menú bar
        menuBar.add(mestresMenu);
        menuBar.add(competicioMenu);
        menuBar.add(ajudaMenu);
        menuBar.add(empresaMenu);
        menuBar.add(sortirMenu);

        setJMenuBar(menuBar);

        // Crear el panel central para la imagen de fondo
        JLabel imageLabel = new JLabel();
        ImageIcon imagenMenu = null;
        try {
            imagenMenu = new ImageIcon(getClass().getClassLoader().getResource("resources/menu-inicial.png"));
            imageLabel.setIcon(imagenMenu);
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        } catch (Exception e) {
            if (imagenMenu == null) {
                imageLabel.setText("No se pudo cargar la imagen.");
                imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            }
        }
        JPanel panelFondo = new JPanel(new BorderLayout());
        panelFondo.add(imageLabel, BorderLayout.CENTER);
        add(panelFondo, BorderLayout.CENTER);

        // Panel para botones flotantes
        JLayeredPane layeredPane = getLayeredPane();

        btnEventos = crearBoton("boton-eventos.png", "Abrir eventos", e -> new EventosListaInterfaz(dataController));
        btnUsuarios = crearBoton("boton-usuarios.png", "Abrir usuarios", e -> new UsuariosListaInterfaz(dataController));
        btnCompeticionIniciar = crearBoton("boton-competicion-iniciar.png", "Iniciar competición", e -> new CompeticionIniciarInterfaz(dataController));
        btnCompeticionResultado = crearBoton("boton-competicion-resultados.png", "Ver resultados", e -> new CompeticionIniciarInterfaz(dataController));

        layeredPane.add(btnEventos, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(btnUsuarios, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(btnCompeticionIniciar, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(btnCompeticionResultado, JLayeredPane.PALETTE_LAYER);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                posicionarBotones();
            }
        });

        posicionarBotones();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton crearBoton(String imagenRuta, String tooltip, java.awt.event.ActionListener actionListener) {
        ImageIcon icono = new ImageIcon(getClass().getClassLoader().getResource("resources/" + imagenRuta));
        Image imagenEscalada = icono.getImage().getScaledInstance(110, 110, Image.SCALE_SMOOTH);
        icono = new ImageIcon(imagenEscalada);

        JButton boton = new JButton(icono);
        boton.setToolTipText(tooltip);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setOpaque(false);
        boton.setBounds(0, 0, 65, 65);
        boton.addActionListener(actionListener);
        return boton;
    }

    private void posicionarBotones() {
        int width = getWidth();
        int height = getHeight();

        btnEventos.setBounds((int) (width * 0.1), (int) (height * 0.35), 65, 65);
        btnUsuarios.setBounds((int) (width * 0.1), (int) (height * 0.55), 65, 65);
        btnCompeticionIniciar.setBounds((int) (width * 0.8), (int) (height * 0.35), 65, 65);
        btnCompeticionResultado.setBounds((int) (width * 0.8), (int) (height * 0.55), 65, 65);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DataController.inicializarDatos(); // Asegura que siempre haya una competición
            new EventosDeportivos();
        });
    }
}