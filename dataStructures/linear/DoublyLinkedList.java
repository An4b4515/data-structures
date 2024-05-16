package dataStructures.linear;

import java.util.Iterator;


public class DoublyLinkedList<T> implements Iterable<T> {
    /* ------------------- Inner Class ------------------- */
    private class Node {
        T item;
        Node next,
             prev;

        Node(Node prev, T item, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }

        @Override
        public String toString() {
            return this.item.toString();
        }
    }
    /* ---------------- End of Inner Class ---------------- */


// Fields:
    private int count = 0;
    private Node head = null, tail = null;


// Constructors:
    public DoublyLinkedList() { } // no-arg constructor ...

    public DoublyLinkedList(T[] arr) {
        for (T item : arr)
            this.addLast( item );
    }


// Info Methods:
    public int size() {
        return this.count;
    }

    public boolean isEmpty() {
        return (this.count == 0);
    }

    public boolean contains(T item) {
        return this.getIndexOfFirstOccurance( item ) >= 0;
    }

    public int getIndexOfFirstOccurance(T item) {
        int i = 0;

        if (null == item) {
            for (Node n = this.head; n != null; n = n.next, ++i)
                if ( null == n.item )
                    return i;
        }
        else {
            for (Node n = this.head; n != null; n = n.next, ++i)
                if ( item.equals(n.item) )
                    return 0;
        }

        return -1;  // Item does not exist in the list ...
    }

    public int getIndexOfLastOccurence(T item) {
        int i = this.count - 1;

        if (null == item) {
            for (Node n = this.tail; n != null; n = n.prev, --i)
                if ( null == n.item )
                    return i;
        }
        else {
            for (Node n = this.tail; n != null; n = n.prev, --i)
                if ( item.equals(n.item) )
                    return i;
        }

        return -1;
    }


// Control Methods:
    public void addLast(T item) {
        this.addBetween(this.tail, item, null);
    }

    public void addFirst(T item) {
        this.addBetween(null, item, this.head);
    }

    public T getFirst() {
        return isEmpty() ? null : this.head.item;
    }

    public T getLast() {
        return isEmpty() ? null : this.tail.item;
    }

    public T getItemOnIndex(int idx) {
        Node n = getNodeAtIndexPosition( idx );
        return (n != null) ? n.item : null;
    }

    public T removeFirst() {
        return remove(this.head);
    }

    public T removeLast() {
        return remove(this.tail);
    }

    public T removeItemOnIndex(int idx) {
        Node n = getNodeAtIndexPosition( idx );
        return remove( n );
    }

    public boolean removeFirstOccurence(T item) {
        Node n = this.head;

        if (null == item) {
            for (; n != null; n = n.next)
                if ( null == n.item ) {
                    remove( n );
                    return true;
                }
        }
        else {
            for (; n != null; n = n.next)
                if ( item.equals(n.item) ) {
                    remove( n );
                    return true;
                }
        }

        return false;
    }

    public boolean removeLastOccurence(T item) {
        Node n = this.tail;

        if (null == item) {
            for (; n != null; n = n.prev)
                if ( null == n.item ) {
                    remove( n );
                    return true;
                }
        }
        else {
            for (; n != null; n = n.prev)
                if ( item.equals(n.item) ) {
                    remove( n );
                    return true;
                }
        }

        return false;
    }

    public boolean removeAll(T duplicate) {
        boolean duplicatesFound = false;

        if ( null == duplicate ) {
            for (Node n = this.head; n != null; ) {
                Node temp = n.next;

                if ( null == duplicate ) {
                    duplicatesFound = true;
                    remove(n);
                }

                n = temp;
            }
        }
        else {
            for (Node n = this.head; n != null; ) {
                Node temp = n.next;

                if ( duplicate.equals(n.item) ) {
                    duplicatesFound = true;
                    remove(n);
                }

                n = temp;
            }
        }

        return duplicatesFound;
    }

    public void empty() {
        for (Node n = this.head; n != null; ) {
            Node temp = n.next;
            remove(n);
            n = temp;
        }
    }

    public void inverse() {
        this.tail = this.head;

        Node lastNodeTracker = null;
        for (Node n = this.head; n != null; ) {
            Node temp = n.next;
            lastNodeTracker = n;

            Node prev = n.prev;
            n.prev = n.next;
            n.next = prev;

            n = temp;
        }

        this.head = lastNodeTracker;
    }


// Helper Methods:
    private void addBetween(Node prev, T item, Node next) {
        var n = new Node(prev, item, next);

        if (null != prev)
            prev.next = n;
        else  // If `null`, then it's head ...
            this.head = n;

        if (null != next)
            next.prev = n;
        else  // If `null`, then it's tail ...
            this.tail = n;

        ++this.count;
    }

    private Node getNodeAtIndexPosition(int idx) {
        assert (idx >= 0) && (idx < this.count);

        Node n = null;
        if ( idx < (this.count >> 1) ) {
            n = this.head;
            for (int i = 0; i < idx; n = n.next, ++i);
        }
        else {
            n = this.tail;
            for (int i = this.count-1; i > idx; n = n.prev, --i);
        }

        return n;
    }

    private T remove(Node n) {
        if (null == n)
            return null;

        T item_n = n.item;
        Node prev_n = n.prev;
        Node next_n = n.next;

        if (prev_n != null)
            prev_n.next = next_n;
        else
            this.head = next_n;

        if (next_n != null)
            next_n.prev = prev_n;
        else
            this.tail = prev_n;

        clear(n);
        --this.count;

        return item_n;
    }

    private void clear(Node x) {  // Help `GC` ...
        x.item = null;
        x.prev = null;
        x.next = null;
        x = null;
    }


    // For-each loop functionality:
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {  // Anonymous inner-class ...
            private Node curr = DoublyLinkedList.this.head;

            @Override
            public boolean hasNext() {
                return curr != null;
            }

            @Override
            public T next() {
                T item = curr.item;
                curr = curr.next;
                return item;
            }
        };
    }


    // Print Method:
    @Override
    public String toString() {
        if ( this.isEmpty() )
            return "[]";

        var sb = new StringBuilder (
            (this.count << 1) + 1   // Elements, *2 for the commas, +1 for the brackets
        ).append('[');              // (the last item does not have a comma) ...

        for (Node n = this.head; ; ) {
            sb.append( n );

            if ( (n = n.next) == null )
            break;

            sb.append(',');
        }

        return sb.append(']').toString();
    }
}

