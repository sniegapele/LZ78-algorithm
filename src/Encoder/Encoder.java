package Encoder;

import Structures.Node;
import Structures.Pair;
import Structures.Tree;
import Utilities.Utils;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Encoder {
    private Tree dictionary;
    private Utils utils;
    private String currentText;

    public Encoder(String filename, int dictionarySize) throws FileNotFoundException {
        this.dictionary = new Tree(dictionarySize);
        this.utils = new Utils(filename, "en_" + filename);
        currentText = utils.convertFromDecimalToBinary(dictionarySize, 4);
    }

    public void encode() throws IOException {
        byte[] leftovers = new byte[0];
        while (!utils.getInputFileEnded()) {
            byte[] newBytes = utils.loadBytes();
            byte[] currentBytes = new byte[leftovers.length + newBytes.length];
            System.arraycopy(leftovers, 0, currentBytes, 0, leftovers.length);
            System.arraycopy(newBytes, 0, currentBytes, leftovers.length, newBytes.length);
            int startPosition = 0;
            while (startPosition < currentBytes.length) {
                Pair newReceived = dictionary.getCoded(currentBytes, startPosition, utils.getInputFileEnded());
                startPosition = newReceived.getLastVisitedPosition();
                Node receivedNode = newReceived.getNode();
                if (receivedNode == null) {
                    leftovers = new byte[currentBytes.length - startPosition];
                    System.arraycopy(currentBytes, startPosition, leftovers, 0, currentBytes.length - startPosition);
                    currentText = utils.writeBytes(currentText);
                    break;
                }
                startPosition++;
                int position = receivedNode.getParentNumber();
                byte character = receivedNode.getCharacter();
                int positions = countPositions();
                currentText += (utils.convertFromDecimalToBinary(position, positions) +
                        utils.convertFromDecimalToBinary(character, 8));
            }

        }
        while (currentText.length() % 8 != 0) {
            currentText += "0";
        }
        utils.writeBytes(currentText);
    }

    private int countPositions() {
        int length = 0;
        for (int i = -1; i < 16; i++) {
            if (dictionary.getCurrentDictionarySize() > Math.pow(2, i) &&
                    dictionary.getCurrentDictionarySize() <= Math.pow(2, i + 1)) {
                length = Integer.toBinaryString((int) Math.pow(2, i + 1)).length();
            }
        }
        return length;
    }
}
