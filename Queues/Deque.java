/* *****************************************************************************
 *  Name: Timor
 *  Date: 21/09/2021
 *  Description: Coursera Algorithems Part 1 Week 2 HW
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first, last;
    private int size;

    private class Node {
        Item value;
        Node next;
        Node prev;

        Node(Item item) {
            this.value = item;
        }
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (isEmpty()) {
            first = new Node(item);
            first.next = null;
            first.prev = null;
            last = first;
        }
        else {
            Node oldFirst = first;
            first = new Node(item);
            first.next = oldFirst;
            oldFirst.prev = first;
        }
        size++;

    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node oldLast = last;
        last = new Node(item);
        last.next = null;
        if (isEmpty()) {
            first = last;
            last.prev = null;
        }
        else {
            oldLast.next = last;
            last.prev = oldLast;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item res = first.value;

        if (size == 1) {
            last = null;
            first = null;
        }
        else {
            first = first.next;
            first.prev = null;
        }
        size--;
        return res;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item res = last.value;
        if (size == 1) {
            first = null;
            last = null;
        }
        else {
            last = last.prev;
        }
        size--;
        return res;
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.value;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> s = new Deque<>();
        s.addFirst(5);
        s.addFirst(6);
        s.addFirst(1);
        s.addLast(14);
        s.addLast(0);
        s.addLast(11);
        for (int x : s)
            System.out.print(x + " ");
        System.out.println("");
        s.removeFirst();
        s.removeFirst();
        s.removeFirst();
        s.removeFirst();
        s.removeLast();
        s.removeLast();


    }

}
