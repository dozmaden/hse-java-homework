package com.ozmaden.core;

import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NetworkServiceTest {
    private ServerSocket ss;
    private Socket cl;
    private Socket sl;

    private NetworkService client;
    private NetworkService server;

    @org.junit.jupiter.api.Test
    void readLong() throws IOException {
        ss = new ServerSocket(25565);
        cl = new Socket("localhost", 25565);
        sl = ss.accept();

        client = new NetworkService(cl);
        server = new NetworkService(sl);

        client.sendLong(1L);
        assertEquals(server.readLong(), 1L);
    }

    @org.junit.jupiter.api.Test
    void sendLong() throws IOException {
        ss = new ServerSocket(25565);
        cl = new Socket("localhost", 25565);
        sl = ss.accept();

        client = new NetworkService(cl);
        server = new NetworkService(sl);

        client.sendLong(1L);
        assertEquals(server.readLong(), 1L);
    }

    @org.junit.jupiter.api.Test
    void readString() throws IOException {
        ss = new ServerSocket(25565);
        cl = new Socket("localhost", 25565);
        sl = ss.accept();

        client = new NetworkService(cl);
        server = new NetworkService(sl);

        server.sendString("123");
        assertEquals(client.readString(), "123");
    }
}
