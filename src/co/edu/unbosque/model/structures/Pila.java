package co.edu.unbosque.model.structures;

public class Pila<T> {
    private Nodo<T> top;
    private int size;

    public Pila() {
        this.top = null;
        this.size = 0;
    }

    public void push(T element) {
        Nodo<T> newNode = new Nodo<>(element);
        newNode.setNext(top);
        top = newNode;
        size++;
    }

    public T pop() {
        if (isEmpty()) {
            throw new RuntimeException("Error: Intento de extraer de una pila vacía.");
        }
        T data = top.getData();
        top = top.getNext();
        size--;
        return data;
    }

    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Error: La pila está vacía.");
        }
        return top.getData();
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }
}