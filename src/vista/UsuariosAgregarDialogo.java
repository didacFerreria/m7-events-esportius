package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import controlador.DataController;
import modelo.Usuario;

public class UsuariosAgregarDialogo extends JDialog {
    private JTextField txtNombre, txtApellido, txtEmail;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbRol;
    private JButton btnGuardar;
    private DataController dataController;

    public UsuariosAgregarDialogo(JFrame parent, DataController dataController) {
        super(parent, "Agregar Usuario", true);
        this.dataController = dataController;

        setLayout(new GridLayout(6, 2, 10, 10));
        setSize(400, 300);
        setLocationRelativeTo(parent);

        add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        add(txtNombre);

        add(new JLabel("Apellido:"));
        txtApellido = new JTextField();
        add(txtApellido);

        add(new JLabel("Email:"));
        txtEmail = new JTextField();
        add(txtEmail);

        add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        add(txtPassword);

        add(new JLabel("Rol:"));
        cmbRol = new JComboBox<>(new String[]{"Administrador", "Visitante"});
        add(cmbRol);

        btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(this::guardarUsuario);
        add(btnGuardar);

        setVisible(true);
    }

    private void guardarUsuario(ActionEvent e) {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        String rol = (String) cmbRol.getSelectedItem();

        if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Todos los campos son obligatorios.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario nuevoUsuario = new Usuario(nombre, apellido, email, password, rol);
        dataController.agregarUsuario(nuevoUsuario);
        JOptionPane.showMessageDialog(this,
                "Usuario agregado exitosamente.");
        dispose();
    }
}
