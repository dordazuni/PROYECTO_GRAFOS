package co.edu.unbosque.model.dto;

public class RutaDTO {
    private ParadaDTO[] camino; // Secuencia ordenada de paradas desde el origen al destino
    private double costoTotal;  // Sumatoria total del tiempo o distancia

    public RutaDTO(ParadaDTO[] camino, double costoTotal) {
        this.camino = camino;
        this.costoTotal = costoTotal;
    }

    public ParadaDTO[] getCamino() {
        return camino;
    }

    public void setCamino(ParadaDTO[] camino) {
        this.camino = camino;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(double costoTotal) {
        this.costoTotal = costoTotal;
    }
}