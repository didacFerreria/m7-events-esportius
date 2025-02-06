package vista.main;

import javax.swing.*;
import java.awt.*;
import persistencia.UsuarioDAO;
import persistencia.CompeticionDAO;
import persistencia.memoria.UsuarioDAOMemoria;
import persistencia.memoria.CompeticionDAOMemoria;
import vista.EventosListaInterfaz;
import vista.UsuariosListaInterfaz;
import vista.CompeticionIniciarInterfaz;

public class EventosDeportivos extends JFrame {

    private UsuarioDAO usuarioDAO;
    private CompeticionDAO competicionDAO;

    public EventosDeportivos() {
        // Inicializar DAOs (puede cambiarse a una implementación de BD en el futuro)
        usuarioDAO = new UsuarioDAOMemoria();
        competicionDAO = new CompeticionDAOMemoria();

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

        JMenu mestresMenu = new JMenu("Mestres");
        JMenuItem esdevenimentsMenuItem = new JMenuItem("Esdeveniments");
        JMenuItem usuarisMenuItem = new JMenuItem("Usuaris");

        // Conectar con las interfaces de usuarios y eventos
        usuarisMenuItem.addActionListener(e -> new UsuariosListaInterfaz(usuarioDAO));
        esdevenimentsMenuItem.addActionListener(e -> new EventosListaInterfaz(competicionDAO));

        mestresMenu.add(esdevenimentsMenuItem);
        mestresMenu.add(usuarisMenuItem);

        JMenu competicioMenu = new JMenu("Competicio");
        JMenuItem iniciMenuItem = new JMenuItem("Inici"); // Cambio aquí, antes era un JMenu en vez de JMenuItem
        JMenuItem resultatsMenuItem = new JMenuItem("Resultats");

        // Conectar con CompeticionIniciarInterfaz
        iniciMenuItem.addActionListener(e -> new CompeticionIniciarInterfaz(competicionDAO));

        competicioMenu.add(iniciMenuItem);
        competicioMenu.add(resultatsMenuItem);

        JMenu ajudaMenu = new JMenu("Ajuda");
        ajudaMenu.add(new JMenuItem("Cerca"));

        JMenu empresaMenu = new JMenu("L'empresa");
        JMenu sortirMenu = new JMenu("Sortir");
        JMenuItem sortirItem = new JMenuItem("Salir");
        sortirItem.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(EventosDeportivos.this,
                    "¿Estás seguro de que quieres salir?", "Confirmar salida", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        sortirMenu.add(sortirItem);

        // Añadir menús al menú bar
        menuBar.add(mestresMenu);
        menuBar.add(competicioMenu);
        menuBar.add(ajudaMenu);
        menuBar.add(empresaMenu);
        menuBar.add(sortirMenu);

        setJMenuBar(menuBar);

        // Crear el panel central para la imagen
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

        add(imageLabel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EventosDeportivos::new);
    }
}
