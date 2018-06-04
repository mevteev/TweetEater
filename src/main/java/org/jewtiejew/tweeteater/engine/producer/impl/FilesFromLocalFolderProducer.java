package org.jewtiejew.tweeteater.engine.producer.impl;

import org.jewtiejew.tweeteater.engine.producer.Producer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FilesFromLocalFolderProducer implements Producer<Path> {

    private String path;
    private String mask;

    public FilesFromLocalFolderProducer(String path, String mask) {
        this.path = path;
        this.mask = mask;
        if (this.mask == null) {
            this.mask = ".*.*";
        }
    }

    @Override
    public Path produce() {
        return null;
    }

    @Override
    public Stream<Path> produceStream() throws IOException {
        return Files.find(Paths.get(path), Integer.MAX_VALUE,
                (path, attr) -> path.toFile().getName().matches(mask)).filter(Files::isRegularFile);
    }
}
