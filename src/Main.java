import Decoder.Decoder;
import Encoder.Encoder;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        try {
            switch (args.length) {
                case 1:
                    Decoder decoder = new Decoder(args[0]);
                    decoder.decode();
                    break;
                case 2:
                    Encoder encoder = new Encoder(args[0], Integer.parseInt(args[1]));
                    encoder.encode();
            }
        } catch (NumberFormatException e) {
            System.out.println("Vocabulary size should be in range [0, 15]!");
        } catch (IOException e) {
            System.out.println("File not found!");
        }

        long endTime = System.currentTimeMillis();

        System.out.println("Duration: " + (double) (endTime - startTime) / 1000 + " seconds");
    }
}
