/* *****************************************************************************
 *  Name: Timor
 *  Date: 21/09/2021
 *  Description: Coursera Algorithems Part 1 Week 2 HW
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        double n = 1.0;
        RandomizedQueue<String> randQ = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            String str = StdIn.readString();
            if (k == 0) {
                return;
            }
            randQ.enqueue(str);
        }
        for (int i = 0; i < k; i++) {
            System.out.println(randQ.dequeue());
        }
    }
}
