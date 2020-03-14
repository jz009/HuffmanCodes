public class Main {

    public static void main(String[] args) {
    HuffmanEncoder encoder = new HuffmanEncoder("The quick brown fox jumps over the lazy dog");
    System.out.println(encoder.encodeString());
    System.out.println(encoder.decodeString("11010010000101000110110100111000110011111000011111001100111110011101000100100111001100010000010011000110110101110000111010001010011000010101010000101000001010001011001000001001000001110011001111"));
    }
}
