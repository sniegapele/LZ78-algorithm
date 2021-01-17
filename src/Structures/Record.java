package Structures;

public class Record {
    private int parentNumber;
    private byte character;

    public Record(int parentNumber, byte character) {
        this.parentNumber = parentNumber;
        this.character = character;
    }

    public Record(int parentNumber) {
        this.parentNumber = parentNumber;
    }

    public int getParentNumber() {
        return parentNumber;
    }

    public byte getCharacter() {
        return character;
    }
}
