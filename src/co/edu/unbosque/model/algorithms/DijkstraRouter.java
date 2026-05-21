package co.edu.unbosque.model.algorithms;

import co.edu.unbosque.model.dto.ParadaDTO;
import co.edu.unbosque.model.dto.RutaDTO;
import co.edu.unbosque.model.entities.Parada;
import co.edu.unbosque.model.structures.Arista;
import co.edu.unbosque.model.structures.ColaPrioridad;
import co.edu.unbosque.model.structures.Grafo;
import co.edu.unbosque.model.structures.ListaSimple;
import co.edu.unbosque.model.structures.Pila;
import co.edu.unbosque.model.structures.Vertice;

public class DijkstraRouter {

    private Grafo<Parada> grafo;

    public DijkstraRouter(Grafo<Parada> grafo) {
        this.grafo = grafo;
    }

    public RutaDTO calcularRutaOptima(Vertice<Parada> vOrigen, Vertice<Parada> vDestino) {
        ListaSimple<Vertice<Parada>> vertices = grafo.getVertices();
        int n = vertices.size();

        // Estructuras de control mapeadas por el índice del vértice
        double[] distancias = new double[n];
        boolean[] visitados = new boolean[n];
        @SuppressWarnings("unchecked")
        Vertice<Parada>[] antecesores = new Vertice[n];

        // Inicialización
        for (int i = 0; i < n; i++) {
            distancias[i] = Double.MAX_VALUE;
            visitados[i] = false;
            antecesores[i] = null;
        }

        int idxOrigen = obtenerIndiceVertice(vertices, vOrigen);
        distancias[idxOrigen] = 0.0;

        ColaPrioridad<Vertice<Parada>> colaPrioridad = new ColaPrioridad<>();
        colaPrioridad.enqueue(vOrigen, 0.0);

        while (!colaPrioridad.isEmpty()) {
            Vertice<Parada> u = colaPrioridad.dequeue();
            int idxU = obtenerIndiceVertice(vertices, u);

            if (visitados[idxU]) continue;
            visitados[idxU] = true;

            // Optimización: si llegamos al destino, detenemos la exploración
            if (u == vDestino) break;

            ListaSimple<Arista<Parada>> aristas = u.getAristas();
            for (int i = 0; i < aristas.size(); i++) {
                Arista<Parada> arista = aristas.get(i);
                Vertice<Parada> v = arista.getDestino();
                int idxV = obtenerIndiceVertice(vertices, v);

                if (!visitados[idxV]) {
                    double nuevaDistancia = distancias[idxU] + arista.getPeso();
                    if (nuevaDistancia < distancias[idxV]) {
                        distancias[idxV] = nuevaDistancia;
                        antecesores[idxV] = u;
                        colaPrioridad.enqueue(v, nuevaDistancia);
                    }
                }
            }
        }

        int idxDestino = obtenerIndiceVertice(vertices, vDestino);

        // Manejo de caminos inalcanzables
        if (distancias[idxDestino] == Double.MAX_VALUE) {
            throw new RuntimeException("No existe ninguna ruta o conexión disponible entre las paradas seleccionadas.");
        }

        // Reconstrucción del camino usando nuestra Pila personalizada
        Pila<ParadaDTO> pilaCamino = new Pila<>();
        Vertice<Parada> actual = vDestino;

        while (actual != null) {
            Parada p = actual.getData();
            pilaCamino.push(new ParadaDTO(p.getId(), p.getNombre()));
            int idxActual = obtenerIndiceVertice(vertices, actual);
            actual = antecesores[idxActual];
        }

        // Convertir la pila a un arreglo ordenado de origen a destino
        ParadaDTO[] caminoFinal = new ParadaDTO[pilaCamino.size()];
        int i = 0;
        while (!pilaCamino.isEmpty()) {
            caminoFinal[i++] = pilaCamino.pop();
        }

        return new RutaDTO(caminoFinal, distancias[idxDestino]);
    }

    /**
     * Método auxiliar privado para ubicar el índice numérico de un vértice
     */
    private int obtenerIndiceVertice(ListaSimple<Vertice<Parada>> vertices, Vertice<Parada> vertice) {
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i) == vertice) {
                return i;
            }
        }
        return -1;
    }
}