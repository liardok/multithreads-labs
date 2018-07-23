package archiver;

public class Main {

    public static void main(String[] args) {
        ThreadedArchiver archiver = new ThreadedArchiver("d:/IdeaProject", "d:/zip.zip");

        archiver.run();

        try {
            archiver.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Archiving complete!");
        System.out.printf("Archived %s files", archiver.getCount());
    }
}
