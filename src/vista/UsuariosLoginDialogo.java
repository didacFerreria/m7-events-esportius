package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import controlador.DataController;
import modelo.Usuario;

public class UsuariosLoginDialogo extends JDialog {
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnMostrarContrasena;
    private boolean autenticado = false;
    private DataController dataController;

    public UsuariosLoginDialogo(JFrame parent, DataController dataController) {
        super(parent, "Iniciar Sesión", true);
        this.dataController = dataController;

        setLayout(new BorderLayout());
        setSize(400, 300);
        setLocationRelativeTo(parent);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);  // Impedir cierre sin autenticación

        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblImagen = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("resources/usuario-config.png")));
        lblImagen.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelContenido.add(lblImagen);
        panelContenido.add(Box.createVerticalStrut(10));

        JPanel panelUsuario = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelUsuario.add(new JLabel("Usuario (admin):"));
        txtUsuario = new JTextField(20);
        panelUsuario.add(txtUsuario);
        panelContenido.add(panelUsuario);

        JPanel panelContrasena = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelContrasena.add(new JLabel("Contraseña (1234):"));
        txtContrasena = new JPasswordField(15);
        panelContrasena.add(txtContrasena);

        btnMostrarContrasena = new JButton("👁");
        btnMostrarContrasena.setFocusable(false);
        btnMostrarContrasena.addActionListener(e -> {
            if (txtContrasena.getEchoChar() == '\u0000') {
                txtContrasena.setEchoChar('*');
            } else {
                txtContrasena.setEchoChar('\u0000');
            }
        });
        panelContrasena.add(btnMostrarContrasena);
        panelContenido.add(panelContrasena);
        panelContenido.add(Box.createVerticalStrut(10));

        JButton btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.addActionListener(e -> autenticarUsuario());
        panelContenido.add(btnLogin);

        add(panelContenido, BorderLayout.CENTER);
        setVisible(true);
    }

    private void autenticarUsuario() {
        String usuario = txtUsuario.getText();
        String contrasena = new String(txtContrasena.getPassword());

        Usuario usuarioValido = dataController.buscarUsuario(usuario);

        if (usuarioValido != null && usuarioValido.getPassword().equals(contrasena)) {
            autenticado = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isAutenticado() {
        return autenticado;
    }
}
