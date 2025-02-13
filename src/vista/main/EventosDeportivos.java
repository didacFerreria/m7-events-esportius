package vista.main;

import javax.swing.*;
import java.awt.*;
import controlador.DataController;
import vista.EventosListaInterfaz;
import vista.UsuariosListaInterfaz;
import vista.CompeticionIniciarInterfaz;

public class EventosDeportivos extends JFrame {

    private DataController dataController;

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
