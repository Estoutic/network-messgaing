package org.estoutic.http;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {

    private final int port;
    private final ExecutorService executor;

    public HttpServer(int port, int poolSize) {
        this.port = port;
        this.executor = Executors.newFixedThreadPool(poolSize);
    }

    public void run() {

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {

                Socket socket = serverSocket.accept();
                System.out.println("Socket accepted");
                executor.submit(() -> processSocket(socket));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void processSocket(Socket socket) {
        try (

                socket;
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        ) {
            Thread.sleep(10000);
            System.out.println(new String(inputStream.readNBytes(500)));
            byte[] body = Files.readAllBytes(Path.of("/Users/ilaudalov/Desktop/projects/studying/javaEE/network-messgaing/src/main/resources/form.html"));

            outputStream.write(("HTTP/1.1 200 OK\r\n" +
                    "Content-Type: text/html\r\n" +
                    "Content-Length: " + body.length + "\r\n" +
                    "\r\n").getBytes());
            outputStream.write(body);
            outputStream.write(System.lineSeparator().getBytes());

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
