package Structures;


public class Tree {
    private int dictionarySize;
    private int currentDictionarySize;
    private Node tree;

    public Tree(int dictionarySize) {
        if (dictionarySize < 0 || dictionarySize > 15) {
            throw new NumberFormatException();
        }
        this.dictionarySize = (int) Math.pow(2, dictionarySize);
        this.currentDictionarySize = 0;
        this.tree = new Node(this);
    }

    public int getCurrentDictionarySize() {
        return currentDictionarySize;
    }

    public int getDictionarySize() {
        return dictionarySize;
    }

    public void updateCurrentDictionarySize() {
        currentDictionarySize++;
    }

    public Pair getCoded(byte[] bytes, int currentByte, boolean fileEnded) {
        return tree.getCode(bytes, currentByte, currentByte, fileEnded);
    }
}
