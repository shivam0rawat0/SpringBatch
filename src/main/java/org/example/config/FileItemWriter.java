package org.example.config;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;

import java.io.FileWriter;
import java.util.List;

public class FileItemWriter<T>  implements ItemWriter<T> {
    @Value("${output.file.path}")
    private String outputFilePath;
    @Override
    public void write(List<? extends T> items) throws Exception {
        FileWriter fileWriter = new FileWriter(outputFilePath);
        for (T item : items) {
            fileWriter.write(item.toString());
        }
    }
}
