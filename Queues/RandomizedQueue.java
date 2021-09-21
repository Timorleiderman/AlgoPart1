import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* *****************************************************************************
 *  Name: Timor
 *  Date: 21/09/2021
 *  Description: Coursera Algorithems Part 1 Week 2 HW
 **************************************************************************** */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] arr;
    private int N;

    // construct an empty randomized queue
    public RandomizedQueue() {
        arr = (Item[]) new Object[1];
        N = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return N;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (N == arr.length) {
            resize(2 * N);
        }
        arr[N++] = item;

    }

    private void resize(int size) {
        Item[] tempArr = (Item[]) new Object[size];
        for (int idx = 0; idx < N; idx++) {
            tempArr[idx] = arr[idx];
        }
        arr = tempArr;

    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int idx = StdRandom.uniform(N);
        Item res = arr[idx];

        arr[idx] = arr[--N];
        arr[N] = null;

        if (N == arr.length / 4) {
            resize(arr.length / 2);
        }

        return res;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return arr[StdRandom.uniform(N)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedArrayIterator();
    }

    private class RandomizedArrayIterator implements Iterator<Item> {

        private int i;
        private Item[] iterArr;

        public RandomizedArrayIterator() {
            iterArr = (Item[]) new Object[N];
            for (int i = 0; i < N; i++) {
                iterArr[i] = arr[i];
            }
            StdRandom.shuffle(iterArr);

        }

        public boolean hasNext() {
            return i < N;
        }

        public Item next() {
            return iterArr[i++];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> test = new RandomizedQueue<>();
        test.enqueue(1);
        test.enqueue(2);
        test.enqueue(3);
        test.enqueue(4);
        test.enqueue(5);
        test.enqueue(6);
        test.enqueue(7);
        test.enqueue(8);
        for (int x : test) {
            System.out.print(x + " ");
        }
        System.out.println("");
        for (int x : test) {
            System.out.print(x + " ");
        }
        System.out.println("");

        System.out.println(test.dequeue());
        System.out.println(test.dequeue());
        System.out.println(test.dequeue());

        for (int x : test) {
            System.out.print(x + " ");
        }
        System.out.println("");


        System.out.println(test.dequeue());
        System.out.println(test.dequeue());
        System.out.println(test.dequeue());

        for (int x : test) {
            System.out.print(x + " ");
        }
        System.out.println("");
    }

}
