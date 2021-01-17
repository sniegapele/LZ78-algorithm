package Decoder;

import Structures.Record;
import Utilities.Utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Decoder {
    private Utils utils;
    private int dictionarySize;
    private List<Record> dictionary;
    private List<Byte> currentBytes;

    public Decoder(String filename) throws FileNotFoundException {
        this.utils = new Utils(filename, "dec_" + filename);
        dictionary = new ArrayList<>();
        dictionary.add(new Record(-1));
        currentBytes = new ArrayList<>();
    }

    public void decode() throws IOException {
        String text = utils.loadBits(1);
        String number = text.substring(0, 4);
        dictionarySize = (int) Math.pow(2, Integer.parseInt(number, 2));
        String leftovers = text.substring(4);
        while (!utils.getInputFileEnded()) {
            text = leftovers + utils.loadBits();
            leftovers = "";
            boolean newLoadRequired = false;
            int startPosition = 0;
            while (!newLoadRequired) {
                int positionsForParentNumber = countPositions();
                String parentNumber = text.substring(startPosition, startPosition + positionsForParentNumber);
                String character = text.substring(startPosition + positionsForParentNumber, startPosition + positionsForParentNumber + 8);
                startPosition += positionsForParentNumber + 8;
                currentBytes.addAll(getDecoded(Integer.parseInt(parentNumber, 2), character));
                if (startPosition + countPositions() + 8 > text.length()) {
                    newLoadRequired = true;
                    leftovers = text.substring(startPosition);
                }
            }
            int[] intArray = currentBytes.stream().mapToInt(i -> i).toArray();
            byte[] bytesToWrite = new byte[intArray.length];
            for (int i = 0; i < bytesToWrite.length; i++) {
                bytesToWrite[i] = (byte) intArray[i];
            }
            utils.writeBytes(bytesToWrite);
            currentBytes.clear();
        }
    }

    private int countPositions() {
        if (dictionarySize == 1) {
            return 1;
        }

        int tempDictionarySize = dictionary.size();

        for (int i = -1; i < 16; i++) {
            if (tempDictionarySize >= Math.pow(2, i) && tempDictionarySize < Math.pow(2, i + 1)) {
                return Integer.toString((int) Math.pow(2, i), 2).length();
            }
        }
        return 1;
    }

    private ArrayList<Byte> getDecoded(int parentNumber, String ch) {
        byte character = (byte) Integer.parseInt(ch, 2);
        if (!isDictionaryFull()) {
            dictionary.add(new Record(parentNumber, character));
        }
        ArrayList<Byte> encoded = new ArrayList<>();
        encoded.add(character);

        int parent = parentNumber;
        while (parent != 0) {
            Record newRecord = dictionary.get(parent);
            parent = newRecord.getParentNumber();
            encoded.add(0, newRecord.getCharacter());
        }
        return encoded;
    }

    private boolean isDictionaryFull() {
        return dictionary.size() == (dictionarySize + 1);
    }

}
