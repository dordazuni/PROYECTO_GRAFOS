package co.edu.unbosque.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import co.edu.unbosque.model.dto.ConexionDTO;
import co.edu.unbosque.model.dto.ParadaDTO;
import co.edu.unbosque.model.dto.RutaDTO;

public class MainView extends JFrame {

    private ICmdViewListener listener;

    // Componentes del Panel de Paradas
    private JTextField txtParadaId;
    private JTextField txtParadaNombre;
    private JButton btnAgregarParada;
    private JTable tblParadas;
    private DefaultTableModel modeloParadas;

    // Componentes del Panel de Conexiones
    private JComboBox<ParadaDTO> cbConexionOrigen;
    private JComboBox<ParadaDTO> cbConexionDestino;
    private JTextField txtConexionPeso;
    private JButton btnAgregarConexion;
    private JTable tblConexiones;
    private DefaultTableModel modeloConexiones;

    // Componentes del Panel de Planificación (Dijkstra)
    private JComboBox<ParadaDTO> cbRutaOrigen;
    private JComboBox<ParadaDTO> cbRutaDestino;
    private JButton btnBuscarRuta;
    private JTextArea txtResultadoRuta;

    public MainView() {
        // 1. Configuración básica de la ventana principal (JFrame)
        setTitle("Sistema de Gestión de Transporte Público - Panel de Control");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 2. Instanciación obligatoria de todos los componentes en el constructor
        txtParadaId = new JTextField(8);
        txtParadaNombre = new JTextField(15);
        btnAgregarParada = new JButton("Registrar Parada");
        modeloParadas = new DefaultTableModel(new Object[]{"ID Parada", "Nombre de la Estación"}, 0);
        tblParadas = new JTable(modeloParadas);

        cbConexionOrigen = new JComboBox<>();
        cbConexionDestino = new JComboBox<>();
        txtConexionPeso = new JTextField(6);
        btnAgregarConexion = new JButton("Conectar Paradas");
        modeloConexiones = new DefaultTableModel(new Object[]{"Origen", "Destino", "Costo / Peso (Minutos o Km)"}, 0);
        tblConexiones = new JTable(modeloConexiones);

        cbRutaOrigen = new JComboBox<>();
        cbRutaDestino = new JComboBox<>();
        btnBuscarRuta = new JButton("Calcular Ruta Óptima");
        txtResultadoRuta = new JTextArea();
        txtResultadoRuta.setEditable(false);
        txtResultadoRuta.setFont(new Font("Monospaced", Font.PLAIN, 13));

        // 3. Organización y construcción de la interfaz por pestañas (JTabbedPane)
        JTabbedPane pestanasPrincipal = new JTabbedPane();

        // --- PESTAÑA 1: GESTIÓN DE PARADAS ---
        JPanel panelParadas = new JPanel(new BorderLayout());
        JPanel formParadas = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        formParadas.add(new JLabel("Código ID:"));
        formParadas.add(txtParadaId);
        formParadas.add(new JLabel("Nombre Parada:"));
        formParadas.add(txtParadaNombre);
        formParadas.add(btnAgregarParada);

        panelParadas.add(formParadas, BorderLayout.NORTH);
        panelParadas.add(new JScrollPane(tblParadas), BorderLayout.CENTER);

        // --- PESTAÑA 2: CONEXIONES ENTRE PARADAS ---
        JPanel panelConexiones = new JPanel(new BorderLayout());
        JPanel formConexiones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        formConexiones.add(new JLabel("Parada Origen:"));
        formConexiones.add(cbConexionOrigen);
        formConexiones.add(new JLabel("Parada Destino:"));
        formConexiones.add(cbConexionDestino);
        formConexiones.add(new JLabel("Peso/Tiempo:"));
        formConexiones.add(txtConexionPeso);
        formConexiones.add(btnAgregarConexion);

        panelConexiones.add(formConexiones, BorderLayout.NORTH);
        panelConexiones.add(new JScrollPane(tblConexiones), BorderLayout.CENTER);

        // --- PESTAÑA 3: PLANIFICADOR DE RUTAS (DIJKSTRA) ---
        JPanel panelRutas = new JPanel(new BorderLayout());
        JPanel formRutas = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        formRutas.add(new JLabel("Punto de Partida:"));
        formRutas.add(cbRutaOrigen);
        formRutas.add(new JLabel("Punto de Destino:"));
        formRutas.add(cbRutaDestino);
        formRutas.add(btnBuscarRuta);

        JPanel panelSalidaRutas = new JPanel(new BorderLayout());
        panelSalidaRutas.add(new JLabel(" Hoja de Ruta Calculada:"), BorderLayout.NORTH);
        panelSalidaRutas.add(new JScrollPane(txtResultadoRuta), BorderLayout.CENTER);

        panelRutas.add(formRutas, BorderLayout.NORTH);
        panelRutas.add(panelSalidaRutas, BorderLayout.CENTER);

        // Agregar las pestañas estructuradas al contenedor principal
        pestanasPrincipal.addTab("Gestión de Paradas", panelParadas);
        pestanasPrincipal.addTab("Conexiones de Red", panelConexiones);
        pestanasPrincipal.addTab("Búsqueda de Rutas Óptimas", panelRutas);

        add(pestanasPrincipal);

        // 4. Configuración interna de los Listeners Swing
        configurarAccionesUI();
    }

    public void setListener(ICmdViewListener listener) {
        this.listener = listener;
    }

    /**
     * Hace visible la ventana y dispara el evento de inicio hacia el controlador.
     */
    public void start() {
        setVisible(true);
        if (listener != null) {
            listener.onStart();
        }
    }

    /**
     * Mapea los botones físicos de Swing hacia el listener del Controlador.
     */
    private void configurarAccionesUI() {
        btnAgregarParada.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listener != null) {
                    String id = txtParadaId.getText();
                    String nombre = txtParadaNombre.getText();
                    listener.onAgregarParada(id, nombre);
                }
            }
        });

        btnAgregarConexion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listener != null) {
                    ParadaDTO origen = (ParadaDTO) cbConexionOrigen.getSelectedItem();
                    ParadaDTO destino = (ParadaDTO) cbConexionDestino.getSelectedItem();

                    if (origen == null || destino == null) {
                        mostrarMensajeError("Debe seleccionar una parada de origen y una de destino.");
                        return;
                    }

                    try {
                        double peso = Double.parseDouble(txtConexionPeso.getText());
                        listener.onAgregarConexion(origen.getId(), destino.getId(), peso);
                    } catch (NumberFormatException ex) {
                        mostrarMensajeError("El campo de peso o costo debe ser un valor numérico válido.");
                    }
                }
            }
        });

        btnBuscarRuta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listener != null) {
                    ParadaDTO origen = (ParadaDTO) cbRutaOrigen.getSelectedItem();
                    ParadaDTO destino = (ParadaDTO) cbRutaDestino.getSelectedItem();

                    if (origen == null || destino == null) {
                        mostrarMensajeError("Seleccione los puntos de inicio y fin para calcular el trayecto.");
                        return;
                    }
                    listener.onBuscarRuta(origen.getId(), destino.getId());
                }
            }
        });
    }

    /**
     * Recibe los arreglos planos desde el Controlador y repinta los componentes
     * iterando a través de bucles clásicos (sin APIs de colecciones).
     */
    public void actualizarComponentes(ParadaDTO[] paradas, ConexionDTO[] conexiones) {
        // Limpiar los JComboBox y Tablas
        cbConexionOrigen.removeAllItems();
        cbConexionDestino.removeAllItems();
        cbRutaOrigen.removeAllItems();
        cbRutaDestino.removeAllItems();
        modeloParadas.setRowCount(0);
        modeloConexiones.setRowCount(0);

        // Poblar componentes de paradas
        for (int i = 0; i < paradas.length; i++) {
            ParadaDTO p = paradas[i];
            cbConexionOrigen.addItem(p);
            cbConexionDestino.addItem(p);
            cbRutaOrigen.addItem(p);
            cbRutaDestino.addItem(p);

            modeloParadas.addRow(new Object[]{p.getId(), p.getNombre()});
        }

        // Poblar componentes de conexiones
        for (int i = 0; i < conexiones.length; i++) {
            ConexionDTO c = conexiones[i];
            modeloConexiones.addRow(new Object[]{c.getNombreOrigen(), c.getNombreDestino(), c.getPeso()});
        }

        // Limpiar campos de texto de entrada
        txtParadaId.setText("");
        txtParadaNombre.setText("");
        txtConexionPeso.setText("");
    }

    /**
     * Imprime el camino óptimo estructurado de Dijkstra en el panel de resultados.
     */
    public void mostrarResultadosRuta(RutaDTO ruta) {
        txtResultadoRuta.setText("");

        StringBuilder sb = new StringBuilder();
        sb.append("=========================================================\n");
        sb.append("         CÁLCULO DE RUTA MÁS CORTA DISPONIBLE            \n");
        sb.append("=========================================================\n\n");
        sb.append("Trayecto de paradas a seguir:\n");

        ParadaDTO[] camino = ruta.getCamino();
        for (int i = 0; i < camino.length; i++) {
            sb.append(" -> [").append(camino[i].getId()).append("] ").append(camino[i].getNombre());
            if (i < camino.length - 1) {
                sb.append("\n");
            }
        }

        sb.append("\n\n---------------------------------------------------------\n");
        sb.append("COSTO TOTAL DEL VIAJE: ").append(ruta.getCostoTotal()).append(" unidades de medida.\n");
        sb.append("=========================================================\n");

        txtResultadoRuta.setText(sb.toString());
    }

    public void mostrarMensajeExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Operación Exitosa", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error de Validación", JOptionPane.ERROR_MESSAGE);
    }
}