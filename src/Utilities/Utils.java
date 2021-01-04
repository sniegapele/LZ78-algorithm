package Utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class Utils {
    private final int BUFFER_SIZE = 512;
    private FileInputStream inputStream;
    private boolean inputFileEnded;
    private FileOutputStream outputStream;

    public Utils(String inputFile, String outputFile) throws FileNotFoundException {
        inputStream = new FileInputStream(inputFile);
        inputFileEnded = false;
        outputStream = new FileOutputStream(outputFile);
    }

    public byte[] loadBytes() throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        int read = inputStream.read(buffer);

        if (read == BUFFER_SIZE) {
            return buffer;
        }

        inputFileEnded = true;

        if (read < 0) {
            return Arrays.copyOf(buffer, 0);
        }

        return Arrays.copyOf(buffer, read);
    }

    public String writeBytes(String bytes) throws IOException {
        int startEndPosition = bytes.length() - bytes.length() % 8;
        String leftovers = bytes.substring(startEndPosition);
        bytes = bytes.substring(0, startEndPosition);

        outputStream.write(convertFromBinaryToDecimal(bytes));

        return leftovers;
    }

    public boolean getInputFileEnded() {
        return inputFileEnded;
    }

    public String convertFromDecimalToBinary(int number, int precision) {
        return String.format("%" + precision + "s", Integer.toBinaryString(number & 0xFF))
                .replace(' ', '0');
    }

    public byte[] convertFromBinaryToDecimal(String text) {
        byte[] chars = new byte[text.length() / 8];

        for (int i = 0; i < text.length() / 8; i++) {
            chars[i] = (byte)Integer.parseInt(text.substring(8 * i, 8 * (i + 1)), 2);
        }

        return chars;
    }
}
