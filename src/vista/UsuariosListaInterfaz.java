package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import modelo.Usuario;
import persistencia.UsuarioDAO;

public class UsuariosListaInterfaz extends JFrame {
    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;
    private JButton btnAgregar, btnEliminar;
    private UsuarioDAO usuarioDAO;

    public UsuariosListaInterfaz(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO; // Guardamos la referencia al DAO

        setTitle("Gestión de Usuarios");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Etiqueta de título
        JLabel lblTitulo = new JLabel("Lista de Usuarios", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblTitulo, BorderLayout.NORTH);

        // Modelo de la tabla
        String[] columnas = {"Nombre", "Apellido", "Email", "Rol", "Fecha Alta"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaUsuarios = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaUsuarios);
        add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        btnAgregar = new JButton("Agregar Usuario");
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UsuariosAgregarDialogo(UsuariosListaInterfaz.this, usuarioDAO);
                actualizarTabla();
            }
        });
        panelBotones.add(btnAgregar);

        btnEliminar = new JButton("Eliminar Usuario");
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarUsuario();
            }
        });
        panelBotones.add(btnEliminar);

        add(panelBotones, BorderLayout.SOUTH);

        // Cargar usuarios desde UsuarioDAO
        cargarUsuarios();

        setVisible(true);
    }

    private void cargarUsuarios() {
        List<Usuario> usuarios = usuarioDAO.listarUsuarios();
        for (Usuario usuario : usuarios) {
            modeloTabla.addRow(new Object[]{
                usuario.getNombre(), usuario.getApellido(), usuario.getEmail(), usuario.getRol(), usuario.getFechaAlta()
            });
        }
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0); // Limpiar la tabla
        cargarUsuarios(); // Recargar los datos
    }

    private void eliminarUsuario() {
        int selectedRow = tablaUsuarios.getSelectedRow();
        if (selectedRow != -1) {
            String email = (String) modeloTabla.getValueAt(selectedRow, 2); // Obtener email del usuario seleccionado
            int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que quieres eliminar a este usuario?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                usuarioDAO.eliminarUsuario(email);
                actualizarTabla();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un usuario para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
