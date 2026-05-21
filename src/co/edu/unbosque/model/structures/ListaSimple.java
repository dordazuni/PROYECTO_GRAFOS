package co.edu.unbosque.model.structures;

public class ListaSimple<T> {
    private Nodo<T> head;
    private int size;

    public ListaSimple() {
        this.head = null;
        this.size = 0;
    }

    // Agregar elemento al final de la lista
    public void add(T element) {
        Nodo<T> newNode = new Nodo<>(element);
        if (head == null) {
            head = newNode;
        } else {
            Nodo<T> current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);
        }
        size++;
    }

    // Obtener elemento por índice (Esencial para iterar más adelante sin usar Iterators de Java)
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("El índice " + index + " está fuera de los límites de la estructura.");
        }

        Nodo<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current.getData();
    }

    // Retorna el tamaño exacto
    public int size() {
        return size;
    }

    // Verifica si está vacía
    public boolean isEmpty() {
        return size == 0;
    }
}