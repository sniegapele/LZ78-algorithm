package Encoder;

import Structures.Node;
import Structures.Pair;
import Structures.Tree;
import Utilities.Utils;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Encoder {
    private Utils utils;
    private String currentText;
    private Tree dictionary;

    public Encoder(String filename, int dictionarySize) throws FileNotFoundException {
        this.dictionary = new Tree(dictionarySize);
        this.utils = new Utils(filename, "en_" + filename);
        this.currentText = utils.convertFromDecimalToBinary(dictionarySize, 4);
    }

    public void encode() throws IOException {
        byte[] leftovers = new byte[0];
        while (!utils.getInputFileEnded()) {
            byte[] newBytes = utils.loadBytes();
            byte[] currentBytes = new byte[newBytes.length + leftovers.length];
            System.arraycopy(leftovers, 0, currentBytes, 0, leftovers.length);
            System.arraycopy(newBytes, 0, currentBytes, leftovers.length, newBytes.length);
            boolean newLoadNeeded = false;
            int startPosition = 0;
            while (!newLoadNeeded) {
                Pair code = dictionary.getCoded(currentBytes, startPosition, utils.getInputFileEnded());
                Node receivedNode = code.getNode();
                startPosition = code.getNewPosition();
                if (receivedNode == null) {
                    newLoadNeeded = true;
                    leftovers = new byte[currentBytes.length - startPosition];
                    System.arraycopy(currentBytes, startPosition, leftovers, 0,
                            currentBytes.length - startPosition);
                } else {
                    if (startPosition == currentBytes.length) {
                        newLoadNeeded = true;
                        leftovers = new byte[0];
                    }
                    int positionsForNumberInDictionary = countPositions();
                    currentText += utils.convertFromDecimalToBinary(receivedNode.getParentNumber(),
                            positionsForNumberInDictionary)
                            + utils.convertFromDecimalToBinary(receivedNode.getCharacter());
                }
            }
            currentText = utils.writeBytes(currentText);
        }
        while (currentText.length() % 8 != 0) {
            currentText += "0";
        }
        utils.writeBytes(currentText);
    }

    private int countPositions() {
        int dictionarySize = dictionary.getCurrentDictionarySize();
        for (int i = -1; i < 16; i++) {
            if (dictionarySize >= Math.pow(2, i) && dictionarySize < Math.pow(2, i + 1)) {
                return Integer.toString((int) Math.pow(2, i), 2).length();
            }
        }
        return -1;
    }

}
