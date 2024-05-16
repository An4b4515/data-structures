package dataStructures.linear;

public final class Stack<T> {
// Fields:
    private DoublyLinkedList<T> items = new DoublyLinkedList<>(); // Diamond operator (type-inference for type-arguments) ...


// Constructor:
    public Stack() { }


// Info Methods:
    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    public int size() {
        return this.items.size();
    }

    public boolean contains(T item) {
        return this.items.contains(item);
    }


// Control Methods:
    public void push(T item) {
        items.addLast( item );
    }

    public T pop() {
        return items.removeLast();
    }

    public T peek() {
        return items.getLast();
    }


// Print Method:
    @Override
    public String toString() {
        return this.items.toString();
    }
}

