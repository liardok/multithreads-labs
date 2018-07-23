package archiver;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadedArchiver extends Archiver {

    private FileSystem zipfs;
    private ExecutorService es = Executors.newFixedThreadPool(10);
    private Visitor visitor = new Visitor();
    private Path root;

    ThreadedArchiver(String inputDir, String outputFile) {
        super(inputDir, outputFile);
    }

    class Callable implements java.util.concurrent.Callable<Integer> {

        private Path file;

        Callable(Path file) {
            super();
            this.file = file;
        }

        @Override
        public Integer call() throws Exception {
//            FileInputStream in = new FileInputStream(file.toFile());
//            OutputStream out = new Files.newOutputStream(zipfs.getPath(file.getFileName().toString()));
            Files.copy(file, root.resolve("\\"+file.getFileName()), StandardCopyOption.REPLACE_EXISTING);

//            IOUtils.copy(in, out);

            return 0;
        }
    }

    class Visitor extends SimpleFileVisitor<Path> {
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            if (attrs.isRegularFile()) {
                es.submit(new Callable(file));
                incrementCount();
            }
            return FileVisitResult.CONTINUE;
        }
    }

    private void createZipFileSystem() throws IOException {
//        Map<String, Object> env = ImmutableMap.<String, Object>builder()
//                .put("create", "true")
//                .put("userTempFile", Boolean.TRUE)
//                .build();

        Map<String, Object> env = new HashMap<>();
        env.put("create", "true");
        env.put("useTempFile", Boolean.TRUE);

        URI zipURI = URI.create(String.format("jar:file:///%s", this.getOutputFile()));
//        URI zipURI = URI.create(String.format("jar:file:///%s", this.getOutputFile()));
        zipfs = FileSystems.newFileSystem(zipURI, env);
        Iterable<Path> roots = zipfs.getRootDirectories();
        root = roots.iterator().next();

    }

    @Override
    public void run() {
        try {
            this.createZipFileSystem();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // walk input directory using our visitor class
        FileSystem fs = FileSystems.getDefault();
        try {
            Files.walkFileTree(fs.getPath(this.getInputDir()), visitor);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // shutdown ExecutorService and block till tasks are complete
        es.shutdown();
        try {
            es.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            zipfs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
