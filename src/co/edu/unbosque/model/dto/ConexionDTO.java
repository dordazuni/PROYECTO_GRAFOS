package co.edu.unbosque.model.dto;

public class ConexionDTO {
    private String idOrigen;
    private String nombreOrigen;
    private String idDestino;
    private String nombreDestino;
    private double peso;
    private String lineaAutobus;

    public ConexionDTO(String idOrigen, String nombreOrigen, String idDestino, String nombreDestino, double peso, String lineaAutobus) {
        this.idOrigen = idOrigen;
        this.nombreOrigen = nombreOrigen;
        this.idDestino = idDestino;
        this.nombreDestino = nombreDestino;
        this.peso = peso;
        this.lineaAutobus = lineaAutobus;
    }

    public String getIdOrigen() {
        return idOrigen;
    }

    public String getNombreOrigen() {
        return nombreOrigen;
    }

    public String getIdDestino() {
        return idDestino;
    }

    public String getNombreDestino() {
        return nombreDestino;
    }

    public double getPeso() {
        return peso;
    }

    public String getLineaAutobus() {
        return lineaAutobus;
    }
}