package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;
import controlador.DataController;
import modelo.Competicion;

public class EventosListaInterfaz extends JFrame {

    private JTable tablaEventos;
    private DefaultTableModel modeloTabla;
    private JButton btnAgregar;
    private JComboBox<String> cmbEstadoFiltro;
    private JComboBox<String> cmbTipoEventoFiltro;
    private DataController dataController;

    public EventosListaInterfaz(DataController dataController) {
        this.dataController = dataController;

        setTitle("Lista de Eventos");
        setSize(750, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Panel de filtros
        JPanel panelFiltros = new JPanel();
        panelFiltros.add(new JLabel("Filtrar por Estado:"));
        cmbEstadoFiltro = new JComboBox<>(new String[]{"Todos", "Pendiente", "En curso", "Finalizado"});
        panelFiltros.add(cmbEstadoFiltro);

        panelFiltros.add(new JLabel("Filtrar por Tipo de Evento:"));
        cmbTipoEventoFiltro = new JComboBox<>(new String[]{"Todos", "Campionat de Basquet",
                "Cursa de Muntanya", "Competició Natació"});
        panelFiltros.add(cmbTipoEventoFiltro);

        add(panelFiltros, BorderLayout.NORTH);

        // Modelo de la tabla
        String[] columnas = {"Nombre", "Tipo de Evento", "Fecha", "Número de Equipos", "Categoría", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaEventos = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaEventos);
        add(scrollPane, BorderLayout.CENTER);

        // Doble clic para ver detalles avanzados
        tablaEventos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Doble clic
                    int filaSeleccionada = tablaEventos.getSelectedRow();
                    if (filaSeleccionada != -1) {
                        String nombreEvento = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
                        Competicion competicion = dataController.buscarCompeticionPorNombre(nombreEvento);
                        if (competicion != null) {
                            new EventosDetalleDialogo(EventosListaInterfaz.this, competicion);
                        }
                    }
                }
            }
        });

        // Botón para agregar evento
        btnAgregar = new JButton("Agregar Evento");
        btnAgregar.addActionListener(e -> {
            new EventosAgregarDialogo(EventosListaInterfaz.this, dataController);
            actualizarTabla();
        });
        JPanel panelBoton = new JPanel();
        panelBoton.add(btnAgregar);
        add(panelBoton, BorderLayout.SOUTH);

        // Listeners para los filtros
        cmbEstadoFiltro.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                actualizarTabla();
            }
        });

        cmbTipoEventoFiltro.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                actualizarTabla();
            }
        });

        // Cargar eventos desde DataController
        cargarEventos();

        setVisible(true);
    }

    private void cargarEventos() {
        modeloTabla.setRowCount(0); // Limpiar la tabla antes de cargar nuevos datos
        List<Competicion> competiciones = dataController.getCompeticiones();

        String estadoFiltro = (String) cmbEstadoFiltro.getSelectedItem();
        String tipoFiltro = (String) cmbTipoEventoFiltro.getSelectedItem();

        List<Competicion> competicionesFiltradas = competiciones.stream()
                .filter(c -> estadoFiltro.equals("Todos") || c.getEstado().equals(estadoFiltro))
                .filter(c -> tipoFiltro.equals("Todos") || c.getTipoEvento().equals(tipoFiltro))
                .collect(Collectors.toList());

        for (Competicion competicion : competicionesFiltradas) {
            modeloTabla.addRow(new Object[]{
                    competicion.getNombre(), competicion.getTipoEvento(), competicion.getFecha(),
                    competicion.getNumeroEquipos(), competicion.getCategoria(), competicion.getEstado()
            });
        }
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0); // Limpiar la tabla
        cargarEventos(); // Recargar los datos
    }
}
