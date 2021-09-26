/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {


    private boolean solvable;
    private SearchNode solNode;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        MinPQ<SearchNode> minPq = new MinPQ<>();
        MinPQ<SearchNode> minTwinPq = new MinPQ<>();


        minPq.insert(new SearchNode(initial, null));
        Board twin = initial.twin();
        minTwinPq.insert(new SearchNode(twin, null));


        while (!minPq.isEmpty() || !minTwinPq.isEmpty()) {

            SearchNode curNode = minPq.delMin();
            SearchNode curTwinNode = minTwinPq.delMin();
            Board curBoard = curNode.getBoard();
            Board curTwinBoard = curTwinNode.getBoard();
            Board prevBoard;
            Board prevTwinBoard;

            if (curBoard.isGoal()) {
                solNode = curNode;
                solvable = true;
                break;
            }

            if (curTwinBoard.isGoal()) {
                break;
            }

            int moves = curNode.getMoves();
            if (moves > 0) {
                prevBoard = curNode.getPrev().getBoard();
            }
            else {
                prevBoard = null;
            }

            int twinMoves = curTwinNode.getMoves();
            if (twinMoves > 0) {
                prevTwinBoard = curTwinNode.getPrev().getBoard();
            }
            else {
                prevTwinBoard = null;
            }

            for (Board nBoard : curBoard.neighbors()) {
                if (nBoard.equals(prevBoard)) {
                    continue;
                }
                minPq.insert(new SearchNode(nBoard, curNode));
            }

            for (Board nBoard : curTwinBoard.neighbors()) {
                if (nBoard.equals(prevTwinBoard)) {
                    continue;
                }
                minTwinPq.insert(new SearchNode(nBoard, curTwinNode));
            }

        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        return solNode.getMoves();
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }

        Stack<Board> solutionStack = new Stack<>();
        SearchNode sol = solNode;
        while (sol != null) {
            Board board = sol.getBoard();
            solutionStack.push(board);
            sol = sol.getPrev();
        }
        return solutionStack;
    }

    private class SearchNode implements Comparable<SearchNode> {

        private final SearchNode prev;
        private final Board board;
        private int moves;


        SearchNode(Board board, SearchNode prev) {
            this.board = board;
            this.prev = prev;
            if (prev != null) {
                this.moves = prev.getMoves() + 1;
            }
            else {
                this.moves = 0;
            }
        }

        public int compareTo(SearchNode searchNode) {
            if (this.getPriority() > searchNode.getPriority()) {
                return 1;
            }
            if (this.getPriority() < searchNode.getPriority()) {
                return -1;
            }
            return Integer.compare(this.board.manhattan(), searchNode.board.manhattan());
        }

        public int getPriority() {
            return board.manhattan() + moves;
        }

        public Board getBoard() {
            return board;
        }

        public int getMoves() {
            return moves;
        }

        public SearchNode getPrev() {
            return prev;
        }

    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
