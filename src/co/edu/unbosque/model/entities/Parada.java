package co.edu.unbosque.model.entities;

public class Parada {
    private String id;
    private String nombre;

    public Parada(String id, String nombre) {
        // Toda instanciación y asignación se realiza en el constructor
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
}