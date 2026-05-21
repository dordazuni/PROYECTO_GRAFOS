package co.edu.unbosque.view;

import javax.swing.JFrame;

public class MainView extends JFrame {

    private ICmdViewListener listener;

    public void setListener(ICmdViewListener listener) {
        this.listener = listener;
    }

    public void start() {
        configView();
        setVisible(true);
        listener.onStart();
    }

    private void configView() {
        // Toda instanciación y configuración inicial va en el constructor
        setTitle("");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        // Aquí instanciaríamos los paneles y componentes internos
    }
}