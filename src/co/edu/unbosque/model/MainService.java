package co.edu.unbosque.model;

import co.edu.unbosque.model.algorithms.DijkstraRouter;
import co.edu.unbosque.model.dto.ConexionDTO;
import co.edu.unbosque.model.dto.ParadaDTO;
import co.edu.unbosque.model.dto.RutaDTO;
import co.edu.unbosque.model.entities.Parada;
import co.edu.unbosque.model.structures.Arista;
import co.edu.unbosque.model.structures.Grafo;
import co.edu.unbosque.model.structures.ListaSimple;
import co.edu.unbosque.model.structures.Vertice;

public class MainService {

    private Grafo<Parada> grafo;

    public MainService() {
        // Inicialización de la estructura principal desde el constructor
        this.grafo = new Grafo<>();
    }

    /**
     * Requisito 1: Gestión de paradas de autobús (Agregar)
     */
    public void agregarParada(String id, String nombre) {
        if (id == null || id.trim().isEmpty() || nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Error: El ID y el nombre de la parada son obligatorios.");
        }

        if (buscarVerticePorId(id) != null) {
            throw new IllegalArgumentException("Error: Ya existe una parada registrada en el sistema con el ID: " + id);
        }

        Parada nuevaParada = new Parada(id, nombre);
        grafo.addVertice(nuevaParada);
    }

    /**
     * Requisito 2: Gestión de conexiones entre paradas (Agregar)
     */
    public void agregarConexion(String idOrigen, String idDestino, double peso) {
        if (peso <= 0) {
            throw new IllegalArgumentException("Error: El tiempo o distancia de la conexión debe ser mayor a cero.");
        }

        Vertice<Parada> vOrigen = buscarVerticePorId(idOrigen);
        Vertice<Parada> vDestino = buscarVerticePorId(idDestino);

        if (vOrigen == null) {
            throw new IllegalArgumentException("Error: La parada de origen con ID '" + idOrigen + "' no existe.");
        }
        if (vDestino == null) {
            throw new IllegalArgumentException("Error: La parada de destino con ID '" + idDestino + "' no existe.");
        }

        // Se crea la conexión bidireccional estándar para rutas de transporte público
        grafo.addAristaBidireccional(vOrigen, vDestino, peso);
    }

    /**
     * Requisito 1: Listar todas las paradas (Retorna un arreglo plano de DTOs para la Vista)
     */
    public ParadaDTO[] obtenerTodasLasParadas() {
        ListaSimple<Vertice<Parada>> vertices = grafo.getVertices();
        ParadaDTO[] resultado = new ParadaDTO[vertices.size()];

        for (int i = 0; i < vertices.size(); i++) {
            Parada p = vertices.get(i).getData();
            resultado[i] = new ParadaDTO(p.getId(), p.getNombre());
        }
        return resultado;
    }

    /**
     * Requisito 2: Listar todas las conexiones (Retorna un arreglo plano de DTOs para la Vista)
     */
    public ConexionDTO[] obtenerTodasLasConexiones() {
        ListaSimple<Vertice<Parada>> vertices = grafo.getVertices();

        // Calcular el tamaño exacto del arreglo sumando todas las aristas
        int totalConexiones = 0;
        for (int i = 0; i < vertices.size(); i++) {
            totalConexiones += vertices.get(i).getAristas().size();
        }

        ConexionDTO[] resultado = new ConexionDTO[totalConexiones];
        int index = 0;

        for (int i = 0; i < vertices.size(); i++) {
            Vertice<Parada> origen = vertices.get(i);
            ListaSimple<Arista<Parada>> aristas = origen.getAristas();

            for (int j = 0; j < aristas.size(); j++) {
                Arista<Parada> arista = aristas.get(j);
                Vertice<Parada> destino = arista.getDestino();

                resultado[index++] = new ConexionDTO(
                        origen.getData().getId(),
                        origen.getData().getNombre(),
                        destino.getData().getId(),
                        destino.getData().getNombre(),
                        arista.getPeso(),
                        "Ruta Estándar" // Se asigna un valor por defecto para el DTO
                );
            }
        }
        return resultado;
    }

    /**
     * Requisito 4: Búsqueda de rutas óptimas (Delegado al enrutador Dijkstra)
     */
    public RutaDTO buscarRutaOptima(String idOrigen, String idDestino) {
        Vertice<Parada> vOrigen = buscarVerticePorId(idOrigen);
        Vertice<Parada> vDestino = buscarVerticePorId(idDestino);

        if (vOrigen == null || vDestino == null) {
            throw new IllegalArgumentException("Error: Las paradas de origen o destino especificadas no existen en la red.");
        }

        // Principio de Responsabilidad Única: Delegamos el cálculo complejo a la clase especializada
        DijkstraRouter enrutador = new DijkstraRouter(this.grafo);

        return enrutador.calcularRutaOptima(vOrigen, vDestino);
    }

    /**
     * Método auxiliar privado: Búsqueda interna de vértices sin exponer la estructura
     */
    private Vertice<Parada> buscarVerticePorId(String id) {
        ListaSimple<Vertice<Parada>> vertices = grafo.getVertices();
        for (int i = 0; i < vertices.size(); i++) {
            Vertice<Parada> v = vertices.get(i);
            if (v.getData().getId().equals(id)) {
                return v;
            }
        }
        return null; // Retorna null si no lo encuentra, permitiendo el manejo de errores superior
    }
}