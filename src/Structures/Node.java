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
        parentNumber = 0;
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

//    @Override
//    public String toString() {
//        return "Node{" +
//                "numberInDictionary=" + numberInDictionary +
//                ", character=" + character +
//                ", parentNumber=" + parentNumber +
//                ", children=" + children +
//                '}';
//    }
//

    public Pair getCode(byte[] bytes, int startPosition, int currentPosition, boolean fileEnded) {
        Node child = children.get(bytes[currentPosition]);
        if (child != null) {
            if (currentPosition + 1 == bytes.length) {
                if (fileEnded) {
                    return new Pair(currentPosition, child);
                } else {
                    return new Pair(startPosition);
                }
            }
            return child.getCode(bytes, startPosition, currentPosition + 1, fileEnded);
        }
        if (tree.getCurrentDictionarySize() == tree.getDictionarySize()) {
            return new Pair(currentPosition, new Node(bytes[currentPosition], numberInDictionary));
        }
        if (currentPosition + 1 == bytes.length) {
            if (!fileEnded) {
                return new Pair(startPosition, null);
            } else {
                return new Pair(currentPosition, this);
            }
        }
        children.put(bytes[currentPosition], new Node(tree, tree.getCurrentDictionarySize(),
                bytes[currentPosition], numberInDictionary));
        tree.updateCurrentDictionarySize();
        return new Pair(currentPosition, children.get(bytes[currentPosition]));
    }

    public byte getCharacter() {
        return character;
    }

    public int getParentNumber() {
        return parentNumber;
    }
}
