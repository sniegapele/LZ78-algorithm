package Structures;

import java.util.HashMap;
import java.util.Map;

public class Node {
    private Tree tree;
    private int numberInDictionary;
    private byte character;
    private int parentNumber;
    private Map<Byte, Node> children;

    public Node(Tree tree) {
        this.tree = tree;
        numberInDictionary = 0;
        parentNumber = -1;
        children = new HashMap<>();
    }

    public Node(Tree tree, int numberInDictionary, byte character, int parentNumber) {
        this.tree = tree;
        this.numberInDictionary = numberInDictionary;
        this.character = character;
        this.parentNumber = parentNumber;
        children = new HashMap<>();
    }

    public Node(byte character, int parentNumber) {
        this.character = character;
        this.parentNumber = parentNumber;
    }

    public Pair getCode(byte[] bytes, int startPosition, int currentPosition, boolean fileEnded) {
        Node child = children.get(bytes[currentPosition]);
        if (child == null) {
            if (tree.isDictionaryFull()) {
                return new Pair(currentPosition + 1,
                        new Node(bytes[currentPosition], this.numberInDictionary));
            } else {
                tree.updateCurrentDictionarySize();
                children.put(bytes[currentPosition],
                        new Node(tree, tree.getCurrentDictionarySize(), bytes[currentPosition], this.numberInDictionary));
                return new Pair(currentPosition + 1,
                        new Node(bytes[currentPosition], this.numberInDictionary));
            }
        } else {
            if (fileEnded && currentPosition + 1 == bytes.length) {
                return new Pair(currentPosition + 1,
                        new Node(bytes[currentPosition], this.numberInDictionary));
            } else if (!fileEnded && currentPosition + 1 == bytes.length) {
                return new Pair(startPosition, null);
            } else {
                return child.getCode(bytes, startPosition, currentPosition + 1, fileEnded);
            }
        }
    }

    public byte getCharacter() {
        return character;
    }

    public int getParentNumber() {
        return parentNumber;
    }
}
