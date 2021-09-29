/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

    private static final boolean VERT = true;
    private static final boolean HORZ = false;

    private Node root;
    private int size;

    private class Node {

        private Point2D point;
        private Node left;
        private Node right;
        private boolean direction;

        public Node(Point2D p) {
            point = p;
            left = null;
            right = null;
        }
    }

    public KdTree() {
        root = null;
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        root = insert(root, p, VERT);
    }

    private Node insert(Node node, Point2D point, boolean direction) {
        if (node == null) {
            Node n = new Node(point);
            n.direction = direction;
            size++;
            return n;
        }

        if (node.direction == VERT) {
            if (point.x() > node.point.x()) {
                node.right = insert(node.right, point, !node.direction);
            }
            else if (point.x() < node.point.x()) {
                node.left = insert(node.left, point, !node.direction);
            }
            else if (point.y() != node.point.y()) {
                node.right = insert(node.right, point, !node.direction);
            }
        }
        if (node.direction == HORZ) {
            if (point.y() > node.point.y()) {
                node.right = insert(node.right, point, !node.direction);
            }
            else if (point.y() < node.point.y()) {
                node.left = insert(node.left, point, !node.direction);
            }
            else if (point.x() != node.point.x()) {
                node.right = insert(node.right, point, !node.direction);
            }
        }
        return node;

    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        return contains(root, p);
    }

    private boolean contains(Node node, Point2D point) {
        while (node != null) {
            if (node.direction == VERT) {
                if (point.x() > node.point.x()) {
                    node = node.right;
                }
                else if (point.x() < node.point.x()) {
                    node = node.left;
                }
                else if (point.y() != node.point.y()) {
                    node = node.right;
                }
                else return true;
            }
            else {
                // Horizontal direction
                if (point.y() > node.point.y()) {
                    node = node.right;
                }
                else if (point.y() < node.point.y()) {
                    node = node.left;
                }
                else if (point.x() != node.point.x()) {
                    node = node.right;
                }
                else return true;

            }
        }
        return false;
    }

    // draw all points to standard draw
    public void draw() {
        draw(root, 0, 0, 1, 1);
    }

    private void draw(Node node, double xmin, double ymin, double xmax, double ymax) {
        if (node == null) {
            return;
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.03);
        node.point.draw();

        if (node.direction == VERT) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.005);
            RectHV rect = new RectHV(node.point.x(), ymin, node.point.x(), ymax);
            rect.draw();
            draw(node.right, node.point.x(), ymin, xmax, ymax);
            draw(node.left, xmin, ymin, node.point.x(), ymax);
        }
        if (node.direction == HORZ) {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius(0.005);
            RectHV rect = new RectHV(xmin, node.point.y(), xmax, node.point.y());
            rect.draw();
            draw(node.right, xmin, node.point.y(), xmax, ymax);
            draw(node.left, xmin, ymin, xmax, node.point.y());
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }

        Stack<Point2D> pointsInRect = new Stack<Point2D>();
        RectHV rootRect = new RectHV(0, 0, 1, 1);
        range(root, rootRect, rect, pointsInRect);
        return pointsInRect;
    }

    private void range(Node node, RectHV nodeRect, RectHV queryRect, Stack<Point2D> pointsInRect) {
        if (node == null) {
            return;
        }
        if (!nodeRect.intersects(queryRect)) {
            return;
        }

        if (queryRect.contains(node.point)) {
            pointsInRect.push(node.point);
        }

        if (node.direction == VERT) {
            range(node.right,
                  new RectHV(node.point.x(), nodeRect.ymin(), nodeRect.xmax(), nodeRect.ymax()),
                  queryRect, pointsInRect);
            range(node.left,
                  new RectHV(nodeRect.xmin(), nodeRect.ymin(), node.point.x(), nodeRect.ymax()),
                  queryRect, pointsInRect);
        }
        else {
            range(node.right,
                  new RectHV(nodeRect.xmin(), node.point.y(), nodeRect.xmax(), nodeRect.ymax()),
                  queryRect, pointsInRect);
            range(node.left,
                  new RectHV(nodeRect.xmin(), nodeRect.ymin(), nodeRect.xmax(), node.point.y()),
                  queryRect, pointsInRect);
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (isEmpty()) {
            return null;
        }

        Node nearN = new Node(root.point);
        nearN.right = root.right;
        nearN.left = root.left;
        nearN.direction = root.direction;
        RectHV rect = new RectHV(0, 0, 1, 1);
        nearest(root, p, rect, nearN);

        return nearN.point;
    }

    private void nearest(Node node, Point2D queryPoint, RectHV rect, Node nearestNode) {
        if (node == null) return;
        if (queryPoint.distanceSquaredTo(node.point) < queryPoint
                .distanceSquaredTo(nearestNode.point)) {
            nearestNode.point = node.point;
        }

        if (node.direction == VERT) {
            RectHV rightRect = new RectHV(node.point.x(), rect.ymin(), rect.xmax(), rect.ymax());
            RectHV leftRect = new RectHV(rect.xmin(), rect.ymin(), node.point.x(), rect.ymax());

            if (queryPoint.x() >= node.point.x()) {
                nearest(node.right, queryPoint, rightRect, nearestNode);
                if (leftRect.distanceSquaredTo(queryPoint) < queryPoint
                        .distanceSquaredTo(nearestNode.point)) {
                    nearest(node.left, queryPoint, leftRect, nearestNode);
                }
            }
            else {
                nearest(node.left, queryPoint, leftRect, nearestNode);
                if (rightRect.distanceSquaredTo(queryPoint) < queryPoint
                        .distanceSquaredTo(nearestNode.point)) {
                    nearest(node.right, queryPoint, rightRect, nearestNode);
                }
            }
        }
        else {
            RectHV rightRect = new RectHV(rect.xmin(), node.point.y(), rect.xmax(), rect.ymax());
            RectHV leftRect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.point.y());

            if (queryPoint.y() >= node.point.y()) {
                nearest(node.right, queryPoint, rightRect, nearestNode);
                if (leftRect.distanceSquaredTo(queryPoint) < queryPoint
                        .distanceSquaredTo(nearestNode.point)) {
                    nearest(node.left, queryPoint, leftRect, nearestNode);
                }
            }
            else {
                nearest(node.left, queryPoint, leftRect, nearestNode);
                if (rightRect.distanceSquaredTo(queryPoint) < queryPoint
                        .distanceSquaredTo(nearestNode.point)) {
                    nearest(node.right, queryPoint, rightRect, nearestNode);
                }
            }
        }
    }


    public static void main(String[] args) {

        // initialize the two data structures with point from file
        // String filename = args[0];
        // In in = new In(filename);
        // KdTree kdtree = new KdTree();
        // while (!in.isEmpty()) {
        //     double x = in.readDouble();
        //     double y = in.readDouble();
        //     Point2D p = new Point2D(x, y);
        //     kdtree.insert(p);
        // }
        //
        // Point2D check = new Point2D(0.226, 0.577);
        // System.out.println(kdtree.contains(check));
        // RectHV rect = new RectHV(0.01, 0.32, 0.83, 0.88);
        // for (Point2D point : kdtree.range(rect)) {
        //     System.out.println(point);
        // }

    }
}
