package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.json.Json;
import ru.otus.model.Measurement;
import ru.otus.model.MeasurementMixin;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class ResourcesFileLoader implements Loader {
    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        //читает файл, парсит и возвращает результат

        //работающий вариант с Gson
//        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
//            String json = Json.createReader(inputStream).readValue().toString();
//            Type type = new TypeToken<List<Measurement>>(){}.getType();
//            return new Gson().fromJson(json, type);
//        } catch (IOException e) {
//            throw new FileProcessException(e);
//        }

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.addMixIn(Measurement.class, MeasurementMixin.class);
            return objectMapper.readerForListOf(Measurement.class).readValue(inputStream);
        } catch (IOException e) {
            throw new FileProcessException(e);
        }

    }
}
