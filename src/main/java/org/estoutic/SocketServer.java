package org.estoutic;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8081);
             Socket socket = serverSocket.accept();
             DataInputStream inputStream = new DataInputStream(socket.getInputStream());
             DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());) {

            System.out.println(inputStream.readUTF());
            outputStream.writeUTF("Hello from server");


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
