/* *****************************************************************************
 *  Name:    Devin Plumb
 *  NetID:   dplumb
 *  Precept: P06
 *
 *  Description:  Immutable data type which implements an A* search.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class Solver {

    // when the goal board is found, this is the associated searchnode
    private final SearchNode endNode;

    private class SearchNode implements Comparable<SearchNode> {
        private final Board board; // the board object for each searchnode
        // the searchnode associated with the board that spawned this neighbor
        private SearchNode previous;
        // the number of previous nodes indirectly linked to this one
        private int moves;

        // constructor for an unconnected Node, given some board
        public SearchNode(Board board) {
            this.board = board;
            this.previous = null;
            this.moves = 0;
        }

        // allows SearchNode to implement Comparable, using manhattan value
        // plus the number of previous moves
        // this lets us use a MinPQ
        public int compareTo(SearchNode that) {
            return Integer.compare(this.moves + this.board.manhattan(),
                                   that.moves + that.board.manhattan());
        }

        // links a searchnode to a family/tree of searchnodes, given its parent
        public void attach(SearchNode that) {
            this.moves = that.moves + 1;
            this.previous = that;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null || !initial.isSolvable())
            throw new IllegalArgumentException("unsolvable board");

        SearchNode min = new SearchNode(initial);
        MinPQ<SearchNode> pq = new MinPQ<>();

        while (!min.board.isGoal()) {
            for (Board board : min.board.neighbors()) {
                if (min.moves == 0 || !board.equals(min.previous.board)) {
                    SearchNode node = new SearchNode(board);
                    node.attach(min);
                    pq.insert(node);
                }
            }
            min = pq.delMin();
        }
        endNode = min;
    }

    // min number of moves to solve initial board
    public int moves() {
        return endNode.moves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        Stack<Board> stack = new Stack<Board>(); // reverses the order
        SearchNode node = endNode;
        while (node != null) {
            stack.push(node.board);
            node = node.previous;
        }
        return stack;
    }


    // test client
    public static void main(String[] args) {

        Stopwatch stopwatch = new Stopwatch();

        In in = new In(args[0]);
        int n = in.readInt();
        int[][] input = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                input[i][j] = in.readInt();
        Board initial = new Board(input);

        if (!initial.isSolvable())
            StdOut.println("no solution");
        else {
            Solver solver = new Solver(initial);
            StdOut.println("min # of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

        StdOut.printf("elapsed time     = %5.3f \n", stopwatch.elapsedTime());
    }

}
