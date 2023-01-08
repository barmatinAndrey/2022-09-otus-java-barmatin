package ru.otus.service;

import java.util.HashMap;
import java.util.Map;

public class UserAuthServiceImpl implements UserAuthService {
    private final Map<String, String> loginPasswordMap;

    public UserAuthServiceImpl() {
        loginPasswordMap = new HashMap<>();
        loginPasswordMap.put("login", "password");
    }

    @Override
    public boolean authenticate(String login, String password) {
        String pwd = loginPasswordMap.get(login);
        return pwd != null && pwd.equals(password);
    }

}
