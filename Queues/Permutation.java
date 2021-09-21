/* *****************************************************************************
 *  Name: Timor
 *  Date: 21/09/2021
 *  Description: Coursera Algorithems Part 1 Week 2 HW
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> randQ = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            String str = StdIn.readString();
            randQ.enqueue(str);
        }
        for (int i = 0; i < k; i++) {
            System.out.println(randQ.dequeue());
        }
    }
}
