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
        Arrays.sort(points);
        ArrayList<LineSegment> foundSegments = new ArrayList<LineSegment>();
        // Point[] tempPoints = Arrays.copyOf(points, points.length);
        Point[] tempPoints = points.clone();
        for (int i = 0; i < tempPoints.length - 3; i++) {
            Point originP = points[i];
            Arrays.sort(tempPoints, originP.slopeOrder());

            for (int begin = 1, end = 2; end < tempPoints.length; end++) {
                while (end < tempPoints.length && originP.slopeTo(tempPoints[begin]) == originP
                        .slopeTo(tempPoints[end])) {
                    end++;
                }
                if ((end - begin >= 3) && (originP.compareTo(tempPoints[begin]) < 0)) {
                    foundSegments.add(new LineSegment(originP, tempPoints[end - 1]));
                }
                begin = end;
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
