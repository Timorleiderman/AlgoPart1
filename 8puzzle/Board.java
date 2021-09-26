/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;

public class Board {

    private final int[][] blocks;
    private final int size;
    private int blankX; // blak spot index
    private int blankY; // blak spot index
    private int hammingDist;
    private int manhattanDist;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {

        if (tiles == null) {
            throw new IllegalArgumentException();
        }
        blocks = tiles.clone();
        size = blocks.length;
        hammingDist = 0;
        manhattanDist = 0;
        int posIdx;
        int xd, yd; // target distance
        int curVal;
        int dis; // temperory distance for debug
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                posIdx = i + ((size - 1) * i) + j + 1;
                curVal = blocks[i][j];
                if (blocks[i][j] == 0) {
                    blankX = j;
                    blankY = i;
                    continue;
                }
                if (curVal != posIdx) {
                    hammingDist++;
                }
                xd = (curVal - 1) / size;
                yd = (curVal - 1) % size;
                dis = Math.abs(xd - i) + Math.abs(yd - j);
                manhattanDist += dis;

            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(size + "\n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                str.append(" " + blocks[i][j]);
            }
            str.append("\n");
        }
        return str.toString();
    }

    // board dimension n
    public int dimension() {
        return size;
    }

    // number of tiles out of place
    public int hamming() {

        return hammingDist;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {

        return manhattanDist;
    }


    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y == this) return true;
        if (y.getClass() != this.getClass()) return false;

        Board tmp = (Board) y;
        if (tmp.size != size) return false;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tmp.blocks[i][j] != blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;

    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighborBoards = new ArrayList<Board>();
        if (blankX > 0) {
            neighborBoards.add(new Board(exchCopy(blankX, blankY, blankX - 1, blankY)));
        }
        if (blankX < size - 1) {
            neighborBoards.add(new Board(exchCopy(blankX, blankY, blankX + 1, blankY)));
        }
        if (blankY > 0) {
            neighborBoards.add(new Board(exchCopy(blankX, blankY, blankX, blankY - 1)));
        }
        if (blankY < size - 1) {
            neighborBoards.add(new Board(exchCopy(blankX, blankY, blankX, blankY + 1)));
        }
        return neighborBoards;
    }

    private int[][] exchCopy(int x0, int y0, int x1, int y1) {
        int[][] copy = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                copy[i][j] = blocks[i][j];
            }
        }
        int tmp = copy[y0][x0];
        copy[y0][x0] = copy[y1][x1];
        copy[y1][x1] = tmp;
        return copy;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int xd = 0, yd = 0;
        if (blankX > 0) {
            xd = blankX - 1;
        }
        else if (blankX < size - 1) {
            xd = blankX + 1;
        }
        if (blankY > 0) {
            yd = blankY - 1;
        }
        else if (blankY < size - 1) {
            yd = blankY + 1;
        }
        Board twin = null;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (blocks[i][j] != 0) {
                    twin = new Board(exchCopy(j, i, xd, yd));
                    return twin;
                }
            }
        }
        return twin;

    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board init = new Board(tiles);
        Board tw = init.twin();
        System.out.println(init.toString());
    }

}
