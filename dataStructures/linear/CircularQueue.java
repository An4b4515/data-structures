package dataStructures.linear;

public class CircularQueue<T> {
// Fields:
    private int count = 0;
    private boolean isDynamic = false;

    private T[] items = null;
    private int front, rear;  // Default values: 0 ...


// Constructors:
    public CircularQueue() {
        this(16, true);
    }

    public CircularQueue(int initialsize) {
        this(initialsize, true);  // Constructor chaining ...
    }

    @SuppressWarnings("unchecked")
    public CircularQueue(int size, boolean dynamic) {
        this.isDynamic = dynamic;
        this.items = (T[]) new Object[size];

        this.front = this.rear = -1;
    }


// Info Methods:
    public boolean isEmpty() {
        return -1 == this.front;
    }

    public boolean isFull() {
        return this.count == this.items.length;
    }

    public int size() {
        return this.count;
    }


// Control Methods:
    public void enqueue(T item) {
        if ( this.isFull() )
            expand();

        if ( this.isEmpty() )
            ++this.front;

        this.rear = (this.rear + 1) % this.items.length;
        this.items[this.rear] = item;

        ++this.count;
    }

    public T dequeue() {
        if ( this.isEmpty() )
            return null;

        T item = this.items[this.front];
        this.items[this.front] = null;

        if (this.front == this.rear)
            this.front = this.rear = -1;

        this.front = (this.front + 1) % this.items.length;

        --this.count;
        return item;
    }

    public T peek() {
        return this.isEmpty() ? null : items[this.front];
    }


// Helper Method:
    private void expand() {
        if ( !this.isDynamic )
            throw new UnsupportedOperationException("OOM: Queue instance not dynamically expandable ...");

        @SuppressWarnings("unchecked")
        var expanded = (T[]) new Object[this.items.length << 1];  // Double the size of the previous capacity ...
        int i = this.front,
            j = 0;

        while (true) {
            expanded[j++] = this.items[i];
            this.items[i] = null; // Overwrite data of previous array ...

            if (i == this.rear)
                break;

            i = (i + 1) % this.items.length;
        }

        this.front = 0;
        this.rear  = j-1;
        this.items = expanded;
    }


// Print Method:
    @Override
    public String toString() {
        if ( this.isEmpty() )
            return "[]";

        var sb = new StringBuilder( (this.count << 1) + 1 ).append('[');

        int i = this.front;
        do {
            sb.append( this.items[i] );

            if ( i == this.rear )
                break;

            sb.append(',');
            i = (i + 1) % this.items.length;
        }
        while ( i != this.front );

        return sb.append(']').toString();
    }
}

