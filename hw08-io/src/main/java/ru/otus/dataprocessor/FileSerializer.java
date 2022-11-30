package ru.otus.dataprocessor;

import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class FileSerializer implements Serializer {
    private final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        //формирует результирующий json и сохраняет его в файл
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(new Gson().toJson(data));
        } catch (IOException e) {
            throw new FileProcessException(e);
        }




    }
}
