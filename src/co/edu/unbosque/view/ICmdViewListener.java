package co.edu.unbosque.view;

public interface ICmdViewListener {
    void onStart();
    void onAgregarParada(String id, String nombre);
    void onAgregarConexion(String idOrigen, String idDestino, double peso);
    void onBuscarRuta(String idOrigen, String idDestino);
}