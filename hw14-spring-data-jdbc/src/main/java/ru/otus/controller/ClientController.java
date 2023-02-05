package ru.otus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.model.Client;
import ru.otus.service.service.DBServiceClient;

import java.util.List;

@RestController
public class ClientController {
    private final DBServiceClient dbServiceClient;

    @Autowired
    public ClientController(DBServiceClient dbServiceClient) {
        this.dbServiceClient = dbServiceClient;
    }

    @PostMapping(path = "/api/client/", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void saveClient(@RequestBody Client client) {
        dbServiceClient.saveClient(client);
    }

    @GetMapping(path = "/api/client/", produces = {MediaType.TEXT_PLAIN_VALUE})
    public String getAll() {
        List<Client> clientList = dbServiceClient.findAll();
        StringBuilder result = new StringBuilder();
        for (Client client: clientList) {
            result.append(client.toString());
            result.append(" ");
        }
        return result.toString();
    }

}
