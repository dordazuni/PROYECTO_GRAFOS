package co.edu.unbosque.model.entities;

public class Conexion {
    private Parada origen;
    private Parada destino;
    private double peso; // Representa la distancia o el tiempo entre paradas
    private String lineaAutobus; // Ej: "Ruta 80" o "Troncal Suba"

    public Conexion(Parada origen, Parada destino, double peso, String lineaAutobus) {
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
        this.lineaAutobus = lineaAutobus;
    }

    public Parada getOrigen() {
        return origen;
    }

    public void setOrigen(Parada origen) {
        this.origen = origen;
    }

    public Parada getDestino() {
        return destino;
    }

    public void setDestino(Parada destino) {
        this.destino = destino;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getLineaAutobus() {
        return lineaAutobus;
    }

    public void setLineaAutobus(String lineaAutobus) {
        this.lineaAutobus = lineaAutobus;
    }
}