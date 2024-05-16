package dataStructures.linear;

public class LinearQueue<T> {
// Field:
    private DoublyLinkedList<T> items = new DoublyLinkedList<>();


// Info Methods:
    public int size() {
        return this.items.size();
    }

    public boolean contains(T item) {
        return this.items.contains( item );
    }

    public boolean isEmpty() {
        return this.items.isEmpty();
    }


// Control Methods:
    public void enqueue(T item) {
        this.items.addLast( item );
    }

    public T dequeue() {
        return this.items.removeFirst();
    }

    public T peek() {
        return this.items.getFirst();
    }


// Print Method:
    @Override
    public String toString() {
        return this.items.toString();
    }
}

