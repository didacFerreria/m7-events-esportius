package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import controlador.DataController;
import modelo.Usuario;

public class UsuariosListaInterfaz extends JFrame {
    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;
    private JButton btnAgregar, btnEliminar;
    private DataController dataController;

    public UsuariosListaInterfaz(DataController dataController) {
        this.dataController = dataController;

        setTitle("Gestión de Usuarios");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JLabel lblTitulo = new JLabel("Lista de Usuarios", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblTitulo, BorderLayout.NORTH);

        String[] columnas = {"Nombre", "Apellido", "Email", "Rol", "Fecha Alta"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaUsuarios = new JTable(modeloTabla);
        add(new JScrollPane(tablaUsuarios), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        btnAgregar = new JButton("Agregar Usuario");
        btnAgregar.addActionListener(e -> {
            new UsuariosAgregarDialogo(this, dataController);
            actualizarTabla();
        });
        panelBotones.add(btnAgregar);

        btnEliminar = new JButton("Eliminar Usuario");
        btnEliminar.addActionListener(e -> eliminarUsuario());
        panelBotones.add(btnEliminar);

        add(panelBotones, BorderLayout.SOUTH);

        cargarUsuarios();
        setVisible(true);
    }

    private void cargarUsuarios() {
        modeloTabla.setRowCount(0);
        List<Usuario> usuarios = dataController.getUsuarios();
        for (Usuario usuario : usuarios) {
            modeloTabla.addRow(new Object[]{
                    usuario.getNombre(), usuario.getApellido(),
                    usuario.getEmail(), usuario.getRol(), usuario.getFechaAlta()
            });
        }
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        cargarUsuarios();
    }

    private void eliminarUsuario() {
        int selectedRow = tablaUsuarios.getSelectedRow();
        if (selectedRow != -1) {
            String email = (String) modeloTabla.getValueAt(selectedRow, 2);
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Estás seguro de que quieres eliminar a este usuario?",
                    "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dataController.eliminarUsuario(email);
                actualizarTabla();
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un usuario para eliminar.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
