/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final LineSegment[] lineSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        checkInput(points);
        Point[] sortedPoints = points.clone();
        ArrayList<LineSegment> foundSegments = new ArrayList<LineSegment>();

        for (int i = 0; i < sortedPoints.length; i++) {
            Arrays.sort(sortedPoints); // back to normal sort
            Arrays.sort(sortedPoints, sortedPoints[i].slopeOrder()); // sort by slope
            int begin = 1;
            int end = 2;
            while (end < sortedPoints.length) {
                while (end < sortedPoints.length
                        && sortedPoints[0].slopeTo(sortedPoints[begin]) == sortedPoints[0]
                        .slopeTo(sortedPoints[end])) {
                    end += 1;
                }
                if ((end - begin >= 3) && (sortedPoints[0].compareTo(sortedPoints[begin]) < 0)) {
                    foundSegments.add(new LineSegment(sortedPoints[0], sortedPoints[end - 1]));
                }
                begin = end;
                end++;

            }
        }

        lineSegments = foundSegments.toArray(new LineSegment[0]);
    }

    private void checkInput(Point[] points) {
        // check if input is null
        if (points == null) {
            throw new IllegalArgumentException();
        }
        // check if any of inputs is null and check duplicates
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
            for (int j = i + 1; j < points.length; j++) {
                if (points[j] == null) throw new IllegalArgumentException();
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.length;
    }
    // the line segments

    public LineSegment[] segments() {
        return lineSegments.clone();
    }


    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
