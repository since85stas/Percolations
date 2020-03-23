/* *****************************************************************************
 *  Name: Stas
 *  Date: 17.03.2020
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Solver {

    // private List<Board> trace;

    private SearchNode finalSearch;

    private int moves;

    private int movesTw;

    private boolean issolvable = false;

    // private int countMP = 0;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initialBoard) {
        if (initialBoard == null) {
            IllegalArgumentException e = new IllegalArgumentException();
            throw  e;
        } else {

            Comparator<SearchNode> comparatorManh = new Comparator<SearchNode>() {
                @Override
                public int compare(SearchNode board0, SearchNode board1) {
                    int pri0 = board0.manh + board0.moves;
                    int pri1 = board1.manh + board1.moves;
                    // int priH0 = board0.board.hamming() + board0.moves;
                    // int priH1 = board1.board.hamming() + board1.moves;
                    // if (board0.equals(board1)) return 0;
                    if (pri1 < pri0) return 1;
                    // else if (pri1 == pri0) {
                    //     if (priH1 < priH0) return 1;
                    //     else return -1;
                    // }
                    else if (pri1 > pri0) return -1;
                    else return 0;
                }
            };

            MinPQ<SearchNode> minPQ = new MinPQ<>(comparatorManh);

            MinPQ<SearchNode> minPQtw = new MinPQ<>(comparatorManh);
            // minPQ.insert(initial);

            // trace = new ArrayList<>();
            SearchNode initial = new SearchNode(initialBoard, 0, null, initialBoard.manhattan());
            SearchNode minBoard = initial;
            minPQ.insert(initial);
            // SearchNode previousBoard;

            Board twin = initialBoard.twin();
            SearchNode initialTw = new SearchNode(twin, 0, null, twin.manhattan());
            SearchNode minBoardTw = initialTw;
            minPQtw.insert(initialTw);
            // SearchNode previousBoardTw;
            int countMP = 0;

            while (!minBoard.board.isGoal() && !minBoardTw.board.isGoal() ) {
                // countMP++;
                // if (countMP > 10000) System.out.println("big c 0");
                // previousBoard = minBoard;
                // previousBoardTw = minBoardTw;
                // System.out.println(previousBoard.toString());
                minBoard = minPQ.delMin();
                // System.out.println("0 "+minBoard.board.toString());
                minBoardTw = minPQtw.delMin();

                if (minBoard.board.isGoal() || minBoardTw.board.isGoal()) break;

                // Iterable<Board> iterable = minBoard.board.neighbors();
                // while (iterable.iterator().hasNext()) {
                //     Board b = iterable.iterator().next();
                //     System.out.println("1 "+b.toString());
                //     if (countMP > 10000) System.out.println("big c 1");
                //     if (minBoard.previous != null) {
                //         if (!b.equals(minBoard.previous.board)) {
                //             countMP++;
                //             minPQ.insert(new SearchNode(b, ++minBoard.previous.moves, minBoard, minBoard.manh));
                //             // System.out.println(b.toString());
                //         }
                //     } else {
                //         countMP++;
                //         minPQ.insert(new SearchNode(b, 1, minBoard, minBoard.manh));
                //     }
                // }
                int neigC = 0;
                for (Board b : minBoard.board.neighbors()
                        ) {
                    int nextMoves = minBoard.moves + 1;
                    if (minBoard.previous != null) {
                        if (!b.equals(minBoard.previous.board)) {
                            countMP++;
                            minPQ.insert(new SearchNode(b, nextMoves, minBoard, b.manhattan()));
                            // System.out.println(b.toString());
                        }
                    } else {
                        countMP++;
                        minPQ.insert(new SearchNode(b, 1, minBoard, b.manhattan()));
                    }
                }

                for (Board b : minBoardTw.board.neighbors()
                ) {
                    int nextMoves = minBoardTw.moves + 1;
                    if (minBoardTw.previous != null) {
                        if (!b.equals(minBoardTw.previous.board)) {
                            countMP++;
                            minPQtw.insert(new SearchNode(b, nextMoves, minBoardTw, b.manhattan()));
                            // System.out.println(b.toString());
                        }
                    } else {
                        countMP++;
                        minPQtw.insert(new SearchNode(b, 1, minBoardTw, b.manhattan()));
                    }
                }
            }
            if (minBoard.board.isGoal() ) {
                issolvable = true;
                finalSearch = minBoard;
                moves = finalSearch.moves;

            } else if (minBoardTw.board.isGoal()) {
                issolvable = false;
            } else {
                // System.out.println("Wrong logic");
            }
             System.out.println("end" + countMP);

            // for (Board b: trace
            //      ) {
            //     System.out.println(b);
            // }
            // System.out.println("End while");
        }
    }

    private class SearchNode {
        private Board board;
        public int moves;
        public int manh;

        private SearchNode previous;

        public SearchNode (Board b, int m, SearchNode p, int manh) {
            board = b;
            moves = m;
            previous = p;
            this.manh = manh;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return issolvable;
    }

    // min number of moves to solve initial board
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        List<Board> trace = new ArrayList<>();
        do {
            trace.add(finalSearch.board);
            finalSearch = finalSearch.previous;
        } while (finalSearch != null);
        if (isSolvable()) {
            List<Board> reverse = new ArrayList<>();
            for (int i = trace.size() - 1; i >= 0; i--) {
                reverse.add(trace.get(i));
            }
            // System.out.println("end");
            return new Iterable<Board>() {
                @Override
                public Iterator<Board> iterator() {
                    return reverse.iterator();
                }
            };
        } else return null;

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
