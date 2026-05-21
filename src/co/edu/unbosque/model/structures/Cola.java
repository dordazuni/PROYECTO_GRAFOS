package co.edu.unbosque.model.structures;

public class Cola<T> {
    private Nodo<T> head;
    private Nodo<T> tail;
    private int size;

    public Cola() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void enqueue(T element) {
        Nodo<T> newNode = new Nodo<>(element);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Error: Intento de extraer de una cola vacía.");
        }
        T data = head.getData();
        head = head.getNext();
        if (head == null) {
            tail = null; // Si la cola queda vacía, el tail también debe ser null
        }
        size--;
        return data;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return size;
    }
}