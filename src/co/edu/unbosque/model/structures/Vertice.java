package co.edu.unbosque.model.structures;

public class Vertice<T> {
    private T data;
    private ListaSimple<Arista<T>> aristas; // Lista de conexiones adyacentes

    public Vertice(T data) {
        this.data = data;
        this.aristas = new ListaSimple<>();
    }

    public void addArista(Vertice<T> destino, double peso) {
        this.aristas.add(new Arista<>(destino, peso));
    }

    public T getData() { return data; }
    public ListaSimple<Arista<T>> getAristas() { return aristas; }
}