package co.edu.unbosque.controller;

import co.edu.unbosque.model.MainService;
import co.edu.unbosque.model.dto.ConexionDTO;
import co.edu.unbosque.model.dto.ParadaDTO;
import co.edu.unbosque.model.dto.RutaDTO;
import co.edu.unbosque.view.ICmdViewListener;
import co.edu.unbosque.view.MainView;

public class MainController {

    private MainView view;
    private MainService service;

    private ICmdViewListener listener = new ICmdViewListener() {

        @Override
        public void onStart() {
            // Al iniciar, le enviamos a la vista los arreglos (vacíos al principio)
            // para que inicialice sus tablas o listas desplegables.
            actualizarVistaConDatos();
        }

        @Override
        public void onAgregarParada(String id, String nombre) {
            try {
                service.agregarParada(id, nombre);
                view.mostrarMensajeExito("Parada agregada exitosamente al sistema.");
                actualizarVistaConDatos(); // Refrescamos las tablas
            } catch (IllegalArgumentException e) {
                view.mostrarMensajeError(e.getMessage());
            } catch (Exception e) {
                view.mostrarMensajeError("Error inesperado al agregar la parada: " + e.getMessage());
            }
        }

        @Override
        public void onAgregarConexion(String idOrigen, String idDestino, double peso) {
            try {
                service.agregarConexion(idOrigen, idDestino, peso);
                view.mostrarMensajeExito("Conexión de transporte establecida.");
                actualizarVistaConDatos(); // Refrescamos las tablas
            } catch (IllegalArgumentException e) {
                view.mostrarMensajeError(e.getMessage());
            } catch (Exception e) {
                view.mostrarMensajeError("Error inesperado al crear la conexión: " + e.getMessage());
            }
        }

        @Override
        public void onBuscarRuta(String idOrigen, String idDestino) {
            try {
                RutaDTO rutaOptima = service.buscarRutaOptima(idOrigen, idDestino);
                view.mostrarResultadosRuta(rutaOptima);
            } catch (RuntimeException e) {
                // Captura errores de negocio como "No existe ruta" o "Paradas no válidas"
                view.mostrarMensajeError(e.getMessage());
            } catch (Exception e) {
                view.mostrarMensajeError("Error crítico al calcular la ruta: " + e.getMessage());
            }
        }
    };


    public MainController() {
        // Toda instanciación se realiza en el constructor
        this.view = new MainView();
        this.service = new MainService();
        // Enlazamos el listener construido con la vista
        this.view.setListener(this.listener);
    }

    /**
     * Método público y único que arranca el flujo de la aplicación.
     */
    public void init() {
        this.view.start();
    }

    /**
     * Método auxiliar privado para centralizar la actualización de datos hacia la vista.
     */
    private void actualizarVistaConDatos() {
        ParadaDTO[] paradas = service.obtenerTodasLasParadas();
        ConexionDTO[] conexiones = service.obtenerTodasLasConexiones();
        view.actualizarComponentes(paradas, conexiones);
    }
}