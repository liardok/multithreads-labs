///*
//package multithreading.sample.ru;
//
//import java.util.Arrays;
//import java.util.concurrent.CountDownLatch;
//
//public class Controller_old {
//
//    public void mArchive(byte [] file) {
//
//        int countThreads = 5;
//
//        final CountDownLatch executionCompleted = new CountDownLatch(countThreads);
//
//        Archiver[] archivers = new Archiver[countThreads];
//        int countParts = file.length / countThreads;
//
//        for(int i = 0; i < countThreads; i++) {
//            byte[] partFile = Arrays.copyOfRange(file, i * countParts, (i + 1) * countParts);
//            Archiver archiver = new Archiver(partFile, executionCompleted);
//            archivers[i] = archiver;
//        }
//
//        Thread[] threads = new Thread[countParts];
//
//        for(int i = 0; i < countThreads; i++) {
//            Thread thread = new Thread(archivers[i]);
//            threads[i] = thread;
//            thread.start();
//        }
//
//        try {
//            executionCompleted.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("Работа потоков закончена");
//
//    }
//}
//*/
