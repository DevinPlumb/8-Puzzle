/* *****************************************************************************
 *  Name:     Devin Plumb
 *  NetID:    dplumb
 *  Precept:  P06
 *
 *  Hours to complete assignment (optional): 8
 *
 **************************************************************************** */

Programming Assignment 4: Slider Puzzle


/* *****************************************************************************
 *  Explain briefly how you represented the Board data type.
 **************************************************************************** */

    I converted the initial 2-dimensional array into a 1-dimensional array to
    save memory, then called functions to compute the hamming value, the
    manhattan value, and the index of the space, and stored each of those (as
    well as the dimension of the board) in instance variables. I never recompute
    the entire hamming or manhattan values, only the difference between the new
    and the old. I pass these values using a private constructor to the
    neighbors of my boards.

/* *****************************************************************************
 *  Explain briefly how you represented a search node
 *  (board + number of moves + previous search node).
 **************************************************************************** */

    I created a private class that stores a board, a previous search node, and
    the number of moves it took to get to the current board as instance
    variables. The class has two functions: a commpareTo function which uses the
    number of moves added to the manhattan value of the board and an attach
    function which links a new SearchNode to its parent tree.

/* *****************************************************************************
 *  Explain briefly how you detected unsolvable puzzles.
 *
 *  What is the order of growth of the running time of your isSolvable()
 *  method in the worst case as function of the board size n? Recall that with
 *  order-of-growth notation, you should discard leading coefficients and
 *  lower-order terms, e.g., n log n or n^3.
 **************************************************************************** */

Description:

    I counted the number of pairwise inversions by using two nested for loops to
    iterate through my array, comparing the values of each pair of tiles. If
    the tile with a smaller index had a larger value (unless the larger tile was
    the 0 tile), I added 1 to the tally.

    Lastly, I reduced this number to its parity using the modulo operator, and
    used the procedure specified for even-odd boards in the spec.

Order of growth of running time:    n^4

    n is the dimension of the board
    n^2 is the number of total spaces (n^2 - 1 tiles)
    (n^2-1)*(n^2-2)/2 = (n^4 - 3n^2 +2)/2 pairwise relationships

    O(n^4)

/* *****************************************************************************
 *  For each of the following instances, give the minimum number of moves to
 *  solve the instance (as reported by your program). Also, give the amount
 *  of time your program takes with both the Hamming and Manhattan priority
 *  functions. If your program can't solve the instance in a reasonable
 *  amount of time (say, 5 minutes) or memory, indicate that instead. Note
 *  that your program may be able to solve puzzle[xx].txt even if it can't
 *  solve puzzle[yy].txt and xx > yy.
 **************************************************************************** */


                 min number          seconds
     instance     of moves     Hamming     Manhattan
   ------------  ----------   ----------   ----------
   puzzle28.txt     28          0.706        0.093
   puzzle30.txt     30          1.328        0.102
   puzzle32.txt     32          Memory       0.499
   puzzle34.txt     34          Memory       0.258
   puzzle36.txt     36          Memory       2.030
   puzzle38.txt     38          Memory       0.661
   puzzle40.txt     40          Memory       0.510
   puzzle42.txt     42          Memory       4.359



/* *****************************************************************************
 *  If you wanted to solve random 4-by-4 or 5-by-5 puzzles, which
 *  would you prefer: a faster computer (say, 2x as fast), more memory
 *  (say 2x as much), a better priority queue (say, 2x as fast),
 *  or a better priority function (say, one on the order of improvement
 *  from Hamming to Manhattan)? Why?
 **************************************************************************** */

    I would prefer a better priority function. Intuitively, we've learned in
    this class that good algorithms often beat good computers. More formally,
    we can see that the number of boards whose manhattan values are calculated
    is, between Hamming and Manhattan, roughly a 7:1 ratio for puzzle28.txt and
    a 13:1 ratio for puzzle30.txt. Seeing as the order of growth of these
    algorithms is non-linear, we can expect this ratio to increase dramatically.

    Meanwhile, a faster computer or better priority queue will only speed up
    the program by a factor of 2; twice as much memory is not enough to compete
    with the number of boards hamming forces you to analyze. A faster computer
    would not be either.

/* *****************************************************************************
 *  Did you fill out the mid-semester feedback form?
 *  If not, please do so now: https://goo.gl/forms/4sTi5ZYaNT3PeMsO2
 **************************************************************************** */

    Yes.

/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */

    None.

/* *****************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 **************************************************************************** */

    None.

/* *****************************************************************************
 *  Describe any serious problems you encountered.
 **************************************************************************** */

    None.

/* *****************************************************************************
 *  If you worked with a partner, assert below that you followed
 *  the protocol as described on the assignment page. Give one
 *  sentence explaining what each of you contributed.
 **************************************************************************** */

    N/A

/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on how much you learned from doing the assignment, and whether
 *  you enjoyed doing it.
 **************************************************************************** */

    None.
