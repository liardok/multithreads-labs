package conc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class App00 {

    public static void main(String[] args) {

        final BlockingQueue<String> queue = new ArrayBlockingQueue<>(16);

        // Producer
        for(int k = 0; k < 3; k++){
            final int finalK = k;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int counter = 0;
                    while (true) {
                        try {
                            Thread.sleep(3000);
                            String data = "elem-" + finalK + "/" + ++counter;
                            queue.put(data);
                            System.out.println("put: " + data);
                        } catch (InterruptedException e) {/*NOP*/}
                    }
                }
            }).start();
        }

        // Consumer
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        System.out.println("... wait for take");
                        String data = queue.take();
                        System.out.println("take: " + data);
                    } catch (InterruptedException e) {/*NOP*/}

                }
            }
        }).start();

    }


}
