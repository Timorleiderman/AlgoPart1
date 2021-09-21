/* *****************************************************************************
 *  Name: Timor
 *  Date: 21/09/2021
 *  Description: Coursera Algorithems Part 1 Week 2 HW
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> randQ = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            String str = StdIn.readString();
            if (k == 0) {
                return;
            }
            if (k > randQ.size()) {
                randQ.enqueue(str);
            }
            else if (StdRandom.uniform() < k) {
                randQ.dequeue();
                randQ.enqueue(str);
            }
        }
        while (!randQ.isEmpty()) {
            System.out.println(randQ.dequeue());
        }
    }
}
