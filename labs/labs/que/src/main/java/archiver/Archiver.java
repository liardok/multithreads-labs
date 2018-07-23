package archiver;

public abstract class Archiver extends Thread {
    private String inputDir;
    private String outputFile;
    private long count;

    Archiver(String inputDir, String outputFile) {
        this.inputDir = inputDir;
        this.outputFile = outputFile;
    }

    public String getInputDir() {
        return inputDir;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public long getCount() {
        return count;
    }

    public void incrementCount() {
        count++;
    }

    public abstract void run();
}
