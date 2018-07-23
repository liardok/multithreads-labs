package labs;

import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

public class ZipDIdeaProject {

    public static void main(String[] args) {

        Path path = Paths.get("d:///IdeaProject");
        Path outputZipFile = Paths.get("d:/zip.zip");
//        URI uri = URI.create("jar:file:///d:/zip.zip");

        ZipDIdeaProject zipDIdeaProject = new ZipDIdeaProject();
        try {
            zipDIdeaProject.zipFolder(outputZipFile, path);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        FileSystem fs = FileSystems.getDefault();

    }

    private void zipFolder(Path zipfile, Path sourceDir) throws IOException {
        Map<String, Object> env = new HashMap<>();
        env.put("create", "true");
        env.put("useTempFile", Boolean.TRUE);

        URI uri = URI.create("jar:" + zipfile.toUri());

        try(FileSystem fileSystem = FileSystems.newFileSystem(uri, env)) {
            Iterable<Path> roots = fileSystem.getRootDirectories();
            Path root = roots.iterator().next();

            Files.walkFileTree(sourceDir, new CopyFileVisitor(root));
        }

    }

}
