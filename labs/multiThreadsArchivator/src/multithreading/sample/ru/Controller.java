package multithreading.sample.ru;

import java.io.*;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by eugene on 29.06.18.
 */
class Controller {

    int countThreads;
//    volatile ConcurrentHashMap<Integer, byte[]> stringConcurrentHashMap;

    volatile ZipOutputStream zipOutputStream;

    private Controller() {}

    Controller(int countThreads) {
        this.countThreads = countThreads;
    }

    void mArchive(byte[] file) throws IOException {

        CountDownLatch countDownLatch = new CountDownLatch(countThreads);

        zipOutputStream = prepareZipFile();

        Thread[] threads = new Thread[countThreads];

        for(int i = 0; i < countThreads; i++) {

            int start = i * file.length/countThreads;
            int finish = (i+1) * file.length/countThreads;

            Archiver archiver = new Archiver(countDownLatch, getPart(file, start, finish), zipOutputStream, start);
            FutureTask futureTask = new FutureTask(archiver);

            threads[i] = new Thread(futureTask);
            threads[i].start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        zipOutputStream.closeEntry();
        zipOutputStream.close();

        System.out.println("Финиш");

    }

    private ZipOutputStream prepareZipFile() throws IOException {
        FileOutputStream fos = new FileOutputStream("compressed.zip");
        ZipOutputStream zipOut = new ZipOutputStream(fos);
//        FileInputStream fis = new FileInputStream("");
        ZipEntry zipEntry = new ZipEntry("rrr_compressed.txt");
        zipOut.putNextEntry(zipEntry);
        return zipOut;
    }

    private byte[] getPart(byte[] file, int start, int finish) {
        return Arrays.copyOfRange(file, start, finish);
    }

    public static byte[] zipBytes(String filename, byte[] input) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        ZipEntry entry = new ZipEntry(filename);
        entry.setSize(input.length);
        zos.putNextEntry(entry);
        zos.write(input);
        zos.closeEntry();
        zos.close();
        return baos.toByteArray();
    }

//    private void zipBytes() throws IOException {
//        FileOutputStream fos = new FileOutputStream("compressed.zip");
//        ZipOutputStream zipOut = new ZipOutputStream(fos);
//        FileInputStream fis = new FileInputStream("");
//        ZipEntry zipEntry = new ZipEntry("rrr_compressed.txt");
//        zipOut.putNextEntry(zipEntry);
//        int currentLenght = 0;
//
//        for(int i = countThreads-1; i < 0; i--) {
//            currentLenght+= stringConcurrentHashMap.get(i).length;
//            zipOut.write(stringConcurrentHashMap.get(i), currentLenght, stringConcurrentHashMap.get(i).length);
//        }
//
//        zipOut.closeEntry();
//        zipOut.close();
//        fos.close();
//
//        fis.close();
//    }
}
