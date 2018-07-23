package labs;

public class Test {
    public static void main(String[] args) {
        SingleElementBufferTimed buffer = new SingleElementBufferTimed();
        new Thread(new ProducerTimed(1, 1000, buffer, 10000)).start();
    }
}
