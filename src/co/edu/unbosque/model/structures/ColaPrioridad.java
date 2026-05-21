package co.edu.unbosque.model.structures;

public class ColaPrioridad<T> {

    private class NodoPrioridad {
        T data;
        double prioridad; // Entre menor sea el valor, mayor es la prioridad (Ej: menor distancia)
        NodoPrioridad next;

        public NodoPrioridad(T data, double prioridad) {
            this.data = data;
            this.prioridad = prioridad;
            this.next = null;
        }
    }

    private NodoPrioridad head;

    public ColaPrioridad() {
        this.head = null;
    }

    public void enqueue(T element, double prioridad) {
        NodoPrioridad newNode = new NodoPrioridad(element, prioridad);

        // Si está vacía o el nuevo nodo tiene más prioridad (menor valor) que la cabeza
        if (head == null || head.prioridad > prioridad) {
            newNode.next = head;
            head = newNode;
        } else {
            NodoPrioridad current = head;
            // Recorremos hasta encontrar la posición correcta
            while (current.next != null && current.next.prioridad <= prioridad) {
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;
        }
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Error: La cola de prioridad está vacía.");
        }
        T data = head.data;
        head = head.next;
        return data;
    }

    public boolean isEmpty() {
        return head == null;
    }
}