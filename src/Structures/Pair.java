package Structures;

public class Pair {
    private int newPosition;
    private Node node;

    public Pair(int newPosition) {
        this.newPosition = newPosition;
        this.node = null;
    }

    public Pair(int newPosition, Node node) {
        this.newPosition = newPosition;
        this.node = node;
    }

    public int getNewPosition() {
        return newPosition;
    }

    public Node getNode() {
        return node;
    }
}
