package multithreading.sample.ru;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.zip.ZipOutputStream;

public class Archiver implements Callable<Integer> {

    private CountDownLatch latch;
    private byte[] partFile;
    private ZipOutputStream zipOutputStream;
    private int start;

    Archiver(CountDownLatch countDownLatch,
             byte[] partBytes, ZipOutputStream stringConcurrentHashMap, int start) {
        this.latch = countDownLatch;
        this.partFile = partBytes;
        this.zipOutputStream = stringConcurrentHashMap;
        this.start = start;
    }

    public Integer call() {
        try {

            synchronized (zipOutputStream) {
                zipBytes();
            }
            System.out.println(new String(partFile, 0, partFile.length));
            latch.countDown();
        } catch (Exception err) {
            err.printStackTrace();
        }
        return 1;
    }

    private void zipBytes() throws IOException {
        zipOutputStream.write(partFile, 0, partFile.length);
    }
}
