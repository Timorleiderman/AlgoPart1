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
    private int sizeN;

    // construct an empty randomized queue
    public RandomizedQueue() {
        arr = (Item[]) new Object[1];
        sizeN = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return sizeN == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return sizeN;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (sizeN == arr.length) {
            resize(sizeN << 1);
        }
        arr[sizeN++] = item;


    }

    private void resize(int size) {
        Item[] tempArr = (Item[]) new Object[size];
        for (int idx = 0; idx < sizeN; idx++) {
            tempArr[idx] = arr[idx];
        }
        arr = tempArr;

    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int idx = StdRandom.uniform(sizeN);
        Item res = arr[idx];

        arr[idx] = arr[--sizeN];
        arr[sizeN] = null;

        if (sizeN > 0 && sizeN == arr.length / 4) {
            resize(arr.length / 2);
        }

        return res;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return arr[StdRandom.uniform(sizeN)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedArrayIterator();
    }

    private class RandomizedArrayIterator implements Iterator<Item> {

        private int i;
        private final Item[] iterArr;
        private final int remain;

        public RandomizedArrayIterator() {
            remain = sizeN;
            iterArr = (Item[]) new Object[sizeN];
            for (i = 0; i < sizeN; i++)
                iterArr[i] = arr[i];
            StdRandom.shuffle(iterArr);
            i = 0;

        }

        public boolean hasNext() {

            return i < remain;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
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
        //     for (int x : test) {
        //         System.out.print(x + " ");
        //     }
        //     System.out.println("");
        //
        //     System.out.println(test.dequeue());
        //     System.out.println(test.dequeue());
        //     System.out.println(test.dequeue());
        //
        //     for (int x : test) {
        //         System.out.print(x + " ");
        //     }
        //     System.out.println("");
        //
        //
        //     System.out.println(test.dequeue());
        //     System.out.println(test.dequeue());
        //     System.out.println(test.dequeue());
        //
        //     for (int x : test) {
        //         System.out.print(x + " ");
        //     }
        //     System.out.println("");
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        for (int j = 1; j <= 10; j++)
            queue.enqueue(j);
        Iterator<Integer> iterator = queue.iterator();
        for (int j = 1; j <= 10; j++)
            System.out.print(iterator.next() + " ");


    }

}
