package labs;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyFileVisitor extends SimpleFileVisitor<Path> {

    private final Path targetPath;
    private Path sourcePath = null;

    public CopyFileVisitor(Path path) {
        this.targetPath = path;
    }

    @Override
    public FileVisitResult preVisitDirectory(final Path dir,
                                             final BasicFileAttributes attrs) throws IOException {
        if (sourcePath == null)
            sourcePath = dir;
//        else
//            Files.createDirectories(targetPath.resolve(sourcePath.relativize(dir).toString()));

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(final Path file,
                                     final BasicFileAttributes attributes) throws IOException {

        if (attributes.isRegularFile()) {
            Files.copy(file, targetPath.resolve("\\"+file.getFileName()), StandardCopyOption.REPLACE_EXISTING);
        }

        return FileVisitResult.CONTINUE;
    }
}
