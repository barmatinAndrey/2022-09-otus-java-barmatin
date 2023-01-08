package ru.otus.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DBServiceClient;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


public class ClientsApiServlet extends HttpServlet {
    private final DBServiceClient dbServiceClient;
    private final Gson gson;

    public ClientsApiServlet(DBServiceClient dbServiceClient, Gson gson) {
        this.dbServiceClient = dbServiceClient;
        this.gson = gson;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Client> clientList = dbServiceClient.findAll();
        StringBuilder result = new StringBuilder();
        for (Client client: clientList) {
            result.append(client.toString());
        }
        response.setContentType("text/plain;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        out.print(result.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String json = req.getReader().lines().collect(Collectors.joining());
        dbServiceClient.saveClient(gson.fromJson(json, Client.class));
    }

}
