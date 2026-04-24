package com.pedrohk.redis.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RedisClient implements AutoCloseable {
    private final Socket socket;
    private final PrintWriter out;
    private final BufferedReader in;

    public RedisClient(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public String send(String command) throws IOException {
        out.println(command);
        return in.readLine();
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}
