package Utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class Utils {
    private final int BUFFER_SIZE = 256;
    private FileInputStream inputStream;
    private boolean inputFileEnded;
    private FileOutputStream outputStream;

    public Utils(String inputFile, String outputFile) throws FileNotFoundException {
        inputStream = new FileInputStream(inputFile);
        inputFileEnded = false;
        outputStream = new FileOutputStream(outputFile);
    }

    public byte[] loadBytes() throws IOException {
        return loadBytes(BUFFER_SIZE);
    }

    private byte[] loadBytes(int numberOfBytes) throws IOException {
        byte[] buffer = new byte[numberOfBytes];
        int read = inputStream.read(buffer);

        if (read == numberOfBytes) {
            return buffer;
        }

        inputFileEnded = true;

        if (read < 0) {
            return Arrays.copyOf(buffer, 0);
        }

        return Arrays.copyOf(buffer, read);
    }

    public String loadBits(int numberOfBytes) throws IOException {
        byte[] buffer = loadBytes(numberOfBytes);
        return convertToBinary(buffer);
    }

    public String loadBits() throws IOException {
        return loadBits(BUFFER_SIZE);
    }

    public String writeBytes(String bytes) throws IOException {
        int startEndPosition = bytes.length() - bytes.length() % 8;
        String leftovers = bytes.substring(startEndPosition);
        bytes = bytes.substring(0, startEndPosition);

        outputStream.write(convertFromBinaryToDecimal(bytes));

        return leftovers;
    }

    public void writeBytes(byte[] bytes) throws IOException {
        outputStream.write(bytes);
    }

    public boolean getInputFileEnded() {
        return inputFileEnded;
    }

    public String convertFromDecimalToBinary(int number, int precision) {
        if (number < 0) {
            number += 256;
        }
        String converted = Integer.toString(number, 2);
        while (converted.length() != precision) {
            converted = "0" + converted;
        }
        return converted;
    }

    public String convertFromDecimalToBinary(int number) {
        return convertFromDecimalToBinary(number, 8);
    }

    public byte[] convertFromBinaryToDecimal(String text) {
        byte[] chars = new byte[text.length() / 8];

        for (int i = 0; i < text.length() / 8; i++) {
            chars[i] = (byte) Integer.parseInt(text.substring(8 * i, 8 * (i + 1)), 2);
        }

        return chars;
    }

    private String convertToBinary(byte[] array) {
        StringBuilder result = new StringBuilder();
        for (byte b : array) {
            String value = convertFromDecimalToBinary(b, 8);
            result.append(value);
        }
        return result.toString();
    }

}
