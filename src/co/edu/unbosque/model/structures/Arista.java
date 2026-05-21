package co.edu.unbosque.model.structures;

public class Arista<T> {
    private Vertice<T> destino;
    private double peso; // Distancia, tiempo o costo

    public Arista(Vertice<T> destino, double peso) {
        this.destino = destino;
        this.peso = peso;
    }

    public Vertice<T> getDestino() { return destino; }
    public double getPeso() { return peso; }
}