package co.edu.unbosque.model.dto;

public class ParadaDTO {
    private String id;
    private String nombre;

    public ParadaDTO(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        // Facilita mostrar el nombre directamente en componentes Swing como JComboBox
        return nombre;
    }
}