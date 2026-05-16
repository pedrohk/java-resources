package com.pedrohk.redis.server;

import com.pedrohk.redis.storage.StorageEngine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RedisServer {
    private final StorageEngine storage = new StorageEngine();
    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    public void start(int port) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (!Thread.currentThread().isInterrupted()) {
                Socket client = serverSocket.accept();
                executor.submit(() -> handleClient(client));
            }
        }
    }

    private void handleClient(Socket client) {
        try (client;
             BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
             PrintWriter out = new PrintWriter(client.getOutputStream(), true)) {

            String line;
            while ((line = in.readLine()) != null) {
                String[] parts = line.split(" ");
                String cmd = parts[0].toUpperCase();

                String response = switch (cmd) {
                    case "SET" -> {
                        storage.set(parts[1], parts[2]);
                        yield "OK";
                    }
                    case "GET" -> Objects.toString(storage.get(parts[1]), "(nil)");
                    case "APPEND" -> {
                        storage.append(parts[1], parts[2]);
                        yield "OK";
                    }
                    case "HSET" -> {
                        storage.hset(parts[1], parts[2], parts[3]);
                        yield "OK";
                    }
                    case "HGET" -> Objects.toString(storage.hget(parts[1], parts[2]), "(nil)");
                    case "HKEYS" -> String.join(",", storage.hkeys(parts[1]));
                    case "HVALS" -> String.join(",", storage.hvals(parts[1]));
                    default -> "ERR unknown command";
                };
                out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
