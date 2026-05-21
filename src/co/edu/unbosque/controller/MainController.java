package co.edu.unbosque.controller;

import co.edu.unbosque.model.MainService;
import co.edu.unbosque.view.ICmdViewListener;
import co.edu.unbosque.view.MainView;

public class MainController {

    private MainView view;
    private MainService service;
    private ICmdViewListener viewListener = new ICmdViewListener() {
        @Override
        public void onStart() {
            // Aquí reaccionamos cuando la vista termina de cargar
            System.out.println("Sistema AutoRescate 24/7 iniciado correctamente.");
            // Podríamos pedirle al service un arreglo de DTOs iniciales y enviarlos a la vista
        }
    };


    public MainController() {
        view = new MainView();
        service = new MainService();
        view.setListener(viewListener);
    }

    public void init() {
        view.start();
    }
}