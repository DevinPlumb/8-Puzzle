/* *****************************************************************************
 *  Name:    Devin Plumb
 *  NetID:   dplumb
 *  Precept: P06
 *
 *  Description:  Data type that models an n-by-n board with sliding tiles.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;


public class Board {

    private final int[] board; // indices of the board in row-major order
    private final int n; // dimension of the board
    private final int hamming; // hamming value
    private final int manhattan; // manhattan value
    private final int nonTile; // index of the zero tile


    // called by neighbors, takes an int array and a series of values and
    // constructs a board object, initializing variables to inputs
    private Board(int[] tiles, int[] swaps) {
        board = tiles.clone();
        n = swaps[0];
        hamming = swaps[1];
        manhattan = swaps[2];
        nonTile = swaps[3];
    }

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;
        board = new int[n * n];
        int k = 0;
        for (int[] i : tiles) {
            for (int j : i) {
                board[k] = j;
                k++;
            }
        }

        nonTile = setNonTile();
        hamming = setHamming();
        manhattan = setManhattan();
    }


    // string representation of this board
    public String toString() {
        StringBuilder stringBoard = new StringBuilder();
        stringBoard.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                stringBoard.append(" " + board[i * n + j] + " ");
            stringBoard.append("\n");
        }
        return stringBoard.toString();
    }


    // tile at (row, col) or 0 if blank
    public int tileAt(int row, int col) {
        if (row < 0 || row >= n || col < 0 || col >= n)
            throw new IllegalArgumentException("invalid index");
        return board[row * n + col];
    }


    // board size n
    public int size() {
        return n;
    }


    // number of tiles out of place
    public int hamming() {
        return hamming;
    }


    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhattan;
    }


    // is this board the goal board?
    public boolean isGoal() {
        return hamming == 0;
    }


    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;

        if (this.n != that.n) return false;
        for (int i = 0; i < n * n; i++)
            if (this.board[i] != that.board[i])
                return false;

        return true;
    }


    // all neighboring boards
    // calls swap and sends values to private board constructor, then loads
    // into queue
    public Iterable<Board> neighbors() {
        Queue<Board> queue = new Queue<Board>();

        if (nonTile / n > 0) {
            int[] array = Arrays.copyOf(board, board.length);
            int[] array2 = swap(array, n, hamming, manhattan,
                                nonTile, nonTile - n);
            Board up = new Board(array, array2);
            queue.enqueue(up);
        }
        if (nonTile / n < n - 1) {
            int[] array = Arrays.copyOf(board, board.length);
            int[] array2 = swap(array, n, hamming, manhattan,
                                nonTile, nonTile + n);
            Board down = new Board(array, array2);
            queue.enqueue(down);
        }
        if (nonTile % n > 0) {
            int[] array = Arrays.copyOf(board, board.length);
            int[] array2 = swap(array, n, hamming, manhattan,
                                nonTile, nonTile - 1);
            Board left = new Board(array, array2);
            queue.enqueue(left);
        }
        if (nonTile % n < n - 1) {
            int[] array = Arrays.copyOf(board, board.length);
            int[] array2 = swap(array, n, hamming, manhattan,
                                nonTile, nonTile + 1);
            Board right = new Board(array, array2);
            queue.enqueue(right);
        }

        return queue;
    }


    // is this board solvable?
    public boolean isSolvable() {
        int inversions = 0;
        for (int i = 0; i < (n * n - 1); i++) {
            for (int j = i + 1; j < (n * n); j++) {
                if (board[i] > board[j] &&
                        board[j] != 0) {
                    inversions++;
                }
            }
        }
        if (n % 2 != 0)
            return inversions % 2 == 0;
        return (inversions + (nonTile / n)) % 2 != 0;
    }


    // sets the hamming value for the first board (board not generated from a
    // neighbor) by adding 1 every time a tile is not in the right place
    private int setHamming() {
        int setHamming = -1;
        for (int i = 0; i < board.length; i++) {
            if (i != (board[i] - 1))
                setHamming++;
        }
        return setHamming;
    }


    // sets the manhattan value for the first board (board not generated from a
    // neighbor) by counting the difference between the adjusted value of every
    // tile and its current index
    private int setManhattan() {
        int setManhattan = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i] != 0)
                setManhattan += Math.abs((board[i] - 1) / n - (i / n))
                        + Math.abs((board[i] - 1) % n - (i % n));
        }
        return setManhattan;
    }


    // finds the zero tile and stores the value of the associated index
    private int setNonTile() {
        int setNonTile = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 0)
                return i;
        }
        return setNonTile;
    }


    // takes in the values of 1 board as well as a tile intended to be flipped
    // with the zero tile, then modifies values in the array and adjusts
    // associated values
    private static int[] swap(int[] array, int length, int h,
                              int m, int space, int tile) {
        int hammingNew = h;
        if (space == (array[tile] - 1))
            hammingNew--;
        if (tile == (array[tile] - 1))
            hammingNew++;
        int manhattanNew = m
                + Math.abs((array[tile] - 1) / length - (space / length))
                + Math.abs((array[tile] - 1) % length - (space % length))
                - Math.abs((array[tile] - 1) / length - (tile / length))
                - Math.abs((array[tile] - 1) % length - (tile % length));

        array[space] = array[tile];
        array[tile] = 0;

        int[] values = { length, hammingNew, manhattanNew, tile };

        return values;
    }


    // unit testing (required)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] input = new int[n][n];
        int space = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                input[i][j] = in.readInt();
                if (input[i][j] == 0)
                    space = i * n + j;
            }
        }
        Board board = new Board(input);

        int[][] input2 = input;
        if (space / n > 0) {
            input2[space / n][space % n] = input2[space / n - 1][space % n];
            input2[space / n - 1][space % n] = 0;
        }
        else if (space / n < n - 1) {
            input2[space / n][space % n] = input2[space / n + 1][space % n];
            input2[space / n + 1][space % n] = 0;
        }
        else if (space % n > 0) {
            input2[space / n][space % n] = input2[space / n][space % n - 1];
            input2[space / n][space % n - 1] = 0;
        }
        else if (space % n < n - 1) {
            input2[space / n][space % n] = input2[space / n][space % n + 1];
            input2[space / n][space % n - 1] = 0;
        }
        Board board2 = new Board(input2);

        StdOut.println(board);
        StdOut.println(board.tileAt(0, 0));
        StdOut.println(board.size());
        StdOut.println(board.hamming());
        StdOut.println(board.manhattan());
        if (board.isGoal())
            StdOut.println("finished puzzle");
        StdOut.println(board.equals(board2));
        Iterable<Board> queue = board.neighbors();
        for (Board i : queue) {
            Iterable<Board> queueNeighbor = i.neighbors();
            for (Board j : queueNeighbor) {
                StdOut.println(j);
                StdOut.println(board.equals(j));
            }
        }
        if (!board.isSolvable())
            StdOut.println("no solution");
    }

}
