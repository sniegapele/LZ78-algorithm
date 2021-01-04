package Structures;

public class Pair {
    private int lastVisitedPosition;
    private Node node;

    public Pair(int lastVisitedPosition) {
        this.lastVisitedPosition = lastVisitedPosition;
        this.node = null;
    }

    public Pair(int lastVisitedPosition, Node node) {
        this.lastVisitedPosition = lastVisitedPosition;
        this.node = node;
    }

    public int getLastVisitedPosition() {
        return lastVisitedPosition;
    }

    public Node getNode() {
        return node;
    }
}
