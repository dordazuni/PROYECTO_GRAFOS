package co.edu.unbosque.model.structures;

public class Grafo<T> {
    private ListaSimple<Vertice<T>> vertices;

    public Grafo() {
        this.vertices = new ListaSimple<>();
    }

    // Agrega una nueva parada al sistema
    public Vertice<T> addVertice(T data) {
        Vertice<T> nuevo = new Vertice<>(data);
        vertices.add(nuevo);
        return nuevo;
    }

    // Crea una conexión dirigida entre dos paradas
    public void addArista(Vertice<T> origen, Vertice<T> destino, double peso) {
        origen.addArista(destino, peso);
    }

    // Para una ruta de transporte público de doble sentido, se llama este método
    public void addAristaBidireccional(Vertice<T> v1, Vertice<T> v2, double peso) {
        v1.addArista(v2, peso);
        v2.addArista(v1, peso);
    }

    public ListaSimple<Vertice<T>> getVertices() {
        return vertices;
    }
}