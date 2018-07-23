package multithreading.sample.ru;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class MZipCreator {

    public static void main(String[] args) throws IOException {

        String fileName = args[0];

        MZipCreator mZipCreator = new MZipCreator();
        byte[] bytes = mZipCreator.readFromFile(fileName);

        CallableController controller = new CallableController(10);
        controller.mArchive(bytes);

        System.out.println("Финиш");
//        System.out.println(new String(bytes, 0, bytes.length));
    }

    /**
     * Считывание файла (параллельной считывание не целесообразно, так как узкое место - диск)
     *
     * Прямо в мейн потоке, так как файл должен быть в переменной к моменту многопоточного архивирования
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    private byte[] readFromFile(String fileName) throws IOException {
        FileInputStream in = new FileInputStream(fileName);
        DataInputStream dis = new DataInputStream(in);
        byte[] datainBytes = new byte[dis.available()];

        dis.readFully(datainBytes);
        dis.close();

        return datainBytes;
    }

}
