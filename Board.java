import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Iterator;

/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
public class Board {

    private int[][] tiles;

    private int[] tilesArr;

    private int N;

    private int humm = -1;

    private int manh = -1;

    private int nullPosition;

    private int twinPos1 = -1;
    private int twinPos2 = -1;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = tiles;
        N = tiles.length;
        tilesArr = new int[N * N - 1];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int oldI = i * N + j;
                if (tiles[i][j] == 0) nullPosition = oldI;
                if ( !(i == N - 1 && j == N - 1) ) tilesArr[oldI] = tiles[i][j];
            }
        }

        if (manhattan() == -1) {
            int count = 0;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    int val = tiles[i][j] - 1;
                    if (val != -1) {
                        int jRef = (val) % N;
                        int iRef = (val) / N;
                        count += Math.abs(iRef - i) + Math.abs(jRef - j);
                    }
                }
            }
            manh = count;
        }

        if (hamming() == -1) {
            int count = 0;
            for (int i = 0; i < tilesArr.length; i++) {
                int desNum = i + 1;
                if (tilesArr[i] != desNum) count++;
            }
            humm = count;
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("" + N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                stringBuilder.append(tiles[i][j] + " ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    // board dimension n
    public int dimension() {
        return N;
    }

    // number of tiles out of place
    public int hamming() {
        // if (humm == 0) {
        //     int count = 0;
        //     for (int i = 0; i < tilesArr.length; i++) {
        //         int desNum = i + 1;
        //         if (tilesArr[i] != desNum) count++;
        //     }
        //     humm = count;
        // }
        return humm;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
            // int count = 0;
            // for (int i = 0; i < N; i++) {
            //     for (int j = 0; j < N; j++) {
            //         int val = tiles[i][j] - 1;
            //         if (val != -1) {
            //             int jRef = (val) % N;
            //             int iRef = (val) / N;
            //             count += Math.abs(iRef - i) + Math.abs(jRef - j);
            //         }
            //     }
            // }
            // manh = count;
        return manh;
    }

    // is this board the goal board?
    public boolean isGoal() {
        int[][] tilesT = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                tilesT[i][j] = i*N + j + 1;
        // tiles[i][j] = i*n + j + 1;
        tilesT[N-1][N-1] = 0;
        Board initial = new Board(tilesT);
        return equals(initial);
    }

    // does this board equal y?
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Board)) return false;
        Board board = (Board) o;
        return this.N == board.N &&
                Arrays.deepEquals(this.tiles, board.tiles);
                // Arrays.equals(tilesArr, board.tilesArr);

    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int neighN = getNeighbrosNum();
        Board[] neighbors = new Board[neighN];
        int j = nullPosition%N;
        int i = nullPosition/N;
        if (i == 0 && j == 0) {
            neighbors[0] = swap(i, j,i + 1, j);
            neighbors[1] = swap(i, j, i,j + 1);
        } else if ( i == N-1 && j == 0 ) {
            neighbors[0] = swap(i, j, i, j + 1);
            neighbors[1] = swap(i, j, i - 1, j);
        } else if ( i == 0 && j == N-1) {
            neighbors[0] = swap(i, j,i + 1, j);
            neighbors[1] = swap(i, j, i,j - 1);
        } else if ( i == N-1 && j == N-1) {
            neighbors[0] = swap(i, j, i,j - 1 );
            neighbors[1] = swap(i, j, i - 1, j );
        } else if ( j == N - 1) {
            neighbors[0] = swap(i,j,i + 1, j);
            neighbors[1] = swap(i, j, i,j - 1);
            neighbors[2] = swap(i, j, i - 1, j );
        } else if ( i == 0 ) {
            neighbors[0] = swap(i, j,i + 1, j);
            neighbors[1] = swap(i, j, i,j - 1);
            neighbors[2] = swap(i, j, i,j + 1);
        } else if ( i == N - 1 ) {
            neighbors[0] = swap(i, j, i,j - 1 );
            neighbors[1] = swap(i, j, i - 1, j );
            neighbors[2] = swap(i, j, i,j + 1 );
        } else if ( j == 0) {
            neighbors[0] = swap(i, j,i + 1, j);
            neighbors[1] = swap(i, j, i,j + 1);
            neighbors[2] = swap(i, j, i - 1,j);
        } else {
            neighbors[0] = swap(i, j, i,j - 1 );
            neighbors[1] = swap(i, j, i - 1, j );
            neighbors[2] = swap(i, j, i,j + 1 );
            neighbors[3] = swap(i,j, i + 1, j);
        }

        Iterable<Board> iterable = new Iterable<Board>() {

            int count = 0;

            @Override
            public Iterator<Board> iterator() {
                return new Iterator<Board>() {

                    @Override
                    public boolean hasNext() {
                        return count < neighbors.length;
                    }

                    @Override
                    public Board next() {
                        Board board = neighbors[count];
                        count++;
                        return board;
                    }
                };
            }
        };
        return iterable;
    }

    private Board swap (int i0,int j0, int iNew, int jNew) {
        int[][] newBoard = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int newI = tiles[i][j];
                newBoard[i][j] = newI ;
            }
        }
        int valNew = tiles[iNew][jNew];
        int valOld = tiles[i0][j0];
        newBoard[i0][j0] = valNew;
        newBoard[iNew][jNew] = valOld;
        return new Board(newBoard);
    }

    private int getNeighbrosNum() {
        int j = nullPosition % N;
        int i = nullPosition/N;
        if (i == N-1 && j == N-1 || i == 0 && j == 0 || i == N-1 && j == 0 || i == 0 && j == N - 1 ) return 2;
        else if (i == N-1 && j != N-1 || i ==0 && j != 0 ) return 3;
        else if (i != N-1 && j == N-1 || i != 0 && j == 0) return 3;
        else return 4;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        // int[][] newBoard = new int[N][N];
        // for (int i = 0; i < N; i++) {
        //     for (int j = 0; j < N; j++) {
        //         int newI = tiles[i][j];
        //         newBoard[i][j] = newI;
        //     }
        // }
        if (twinPos1 == -1 ) {
            for (int i = 0; i < N*N*10; i++) {
                twinPos1 = StdRandom.uniform(N*N);
                if (twinPos1 != nullPosition) break;
            }

            for (int i = 0; i < N*N*10; i++) {
                twinPos2 = StdRandom.uniform(N*N);
                if (twinPos2 != nullPosition && twinPos2 != twinPos1) break;
            }
        }

        int j1 = twinPos1 % N;
        int i1 = twinPos1 / N;
        int j2 = twinPos2 % N;
        int i2 = twinPos2 / N;
        return swap(i1 ,j1, i2, j2);
        // return new Board(newBoard);
    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }

}
