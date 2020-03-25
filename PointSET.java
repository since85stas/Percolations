/* *****************************************************************************
 *  Name:    Alan Turing
 *  NetID:   aturing
 *  Precept: P00
 *
 *  Description:  Prints 'Hello, World' to the terminal window.
 *                By tradition, this is everyone's first program.
 *                Prof. Brian Kernighan initiated this tradition in 1974.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {
    // construct an empty set of points
    public         PointSET(){

    }

    // is the set empty?
    public           boolean isEmpty() {
        return false;
    }

    // number of points in the set
    public               int size() {
        return 0;
    }

    // add the point to the set (if it is not already in the set)
    public              void insert(Point2D p) {
        if (p == null) {
            IllegalArgumentException e = new IllegalArgumentException();
            throw e;
        } else {

        }
    }

    // does the set contain point p?
    public           boolean contains(Point2D p) {
        if (p == null) {
            IllegalArgumentException e = new IllegalArgumentException();
            throw e;
        } else {

        }
        return false;
    }

    // draw all points to standard draw
    public              void draw() {

    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            IllegalArgumentException e = new IllegalArgumentException();
            throw e;
        } else {

        }
        return null;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public           Point2D nearest(Point2D p) {
        if (p == null) {
            IllegalArgumentException e = new IllegalArgumentException();
            throw e;
        } else {

        }
        return null;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args)    {

    }

}
