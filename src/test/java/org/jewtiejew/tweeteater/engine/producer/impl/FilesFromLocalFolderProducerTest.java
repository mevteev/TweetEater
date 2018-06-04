package org.jewtiejew.tweeteater.engine.producer.impl;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class FilesFromLocalFolderProducerTest {

    FilesFromLocalFolderProducer producer;

    @Before
    public void init() {
        producer = new FilesFromLocalFolderProducer("C:\\Users\\user\\IdeaProjects\\tweeteater", ".*.java");
    }

    @Test
    public void testProduce() throws IOException {
        producer.produceStream().forEach(System.out::println);
    }
}
