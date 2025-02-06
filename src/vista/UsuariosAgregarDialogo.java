/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.Usuario;
import persistencia.UsuarioDAO;

public class UsuariosAgregarDialogo extends JDialog {
    private JTextField txtNombre, txtApellido, txtEmail;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbRol;
    private JButton btnGuardar;
    private UsuarioDAO usuarioDAO;

    public UsuariosAgregarDialogo(JFrame parent, UsuarioDAO usuarioDAO) {
        super(parent, "Agregar Usuario", true);
        this.usuarioDAO = usuarioDAO;

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
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarUsuario();
            }
        });
        add(btnGuardar);

        setVisible(true);
    }

    private void guardarUsuario() {
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String email = txtEmail.getText();
        String password = new String(txtPassword.getPassword());
        String rol = (String) cmbRol.getSelectedItem();

        if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario nuevoUsuario = new Usuario(nombre, apellido, email, password, rol);
        usuarioDAO.agregarUsuario(nuevoUsuario);
        JOptionPane.showMessageDialog(this, "Usuario agregado exitosamente.");
        dispose();
    }
}
