package com.pedrohk.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class SimpleHttpServer {
    private final int port;
    private final Map<String, Supplier<String>> routes = new ConcurrentHashMap<>();
    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
    private volatile boolean running = true;

    public SimpleHttpServer(int port) {
        this.port = port;
    }

    public void get(String path, Supplier<String> handler) {
        routes.put(path, handler);
    }

    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (running) {
                Socket client = serverSocket.accept();
                executor.submit(() -> handleRequest(client));
            }
        }
    }

    public void stop() {
        this.running = false;
    }

    private void handleRequest(Socket client) {
        try (client;
             BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
             OutputStream out = client.getOutputStream()) {

            String line = in.readLine();
            if (line == null) return;

            String[] requestLine = line.split(" ");
            if (requestLine.length < 2 || !requestLine[0].equals("GET")) {
                sendResponse(out, 405, "Method Not Allowed");
                return;
            }

            String path = requestLine[1];
            Supplier<String> handler = routes.get(path);

            if (handler != null) {
                sendResponse(out, 200, handler.get());
            } else {
                sendResponse(out, 404, "Not Found");
            }
        } catch (IOException e) {
            System.err.println("Request error: " + e.getMessage());
        }
    }

    private void sendResponse(OutputStream out, int status, String body) throws IOException {
        String response = "HTTP/1.1 " + status + " OK\r\n" +
                "Content-Type: text/plain\r\n" +
                "Content-Length: " + body.length() + "\r\n" +
                "\r\n" +
                body;
        out.write(response.getBytes());
    }
}
