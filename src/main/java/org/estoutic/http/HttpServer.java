package org.estoutic.http;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

public class HttpServer {

    private final int port;

    public HttpServer(int port) {
        this.port = port;
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port);
             Socket socket = serverSocket.accept();

        ) {
            processSocket(socket);

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
            System.out.println(new String(inputStream.readNBytes(500)));
            byte[] body = Files.readAllBytes(Path.of("/Users/ilaudalov/Desktop/projects/studying/javaEE/network-messgaing/src/main/resources/form.html"));

            outputStream.write(("HTTP/1.1 200 OK\r\n" +
                    "Content-Type: text/html\r\n" +
                    "Content-Length: " + body.length + "\r\n" +
                    "\r\n").getBytes());
            outputStream.write(body);
            outputStream.write(System.lineSeparator().getBytes());
            outputStream.write(body);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
