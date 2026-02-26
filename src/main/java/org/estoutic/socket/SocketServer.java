package org.estoutic.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SocketServer {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8081);
             Socket socket = serverSocket.accept();
             DataInputStream inputStream = new DataInputStream(socket.getInputStream());
             DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
             Scanner scanner = new Scanner(System.in);

             ) {
            String request = inputStream.readUTF();
            while (!request.equals("exit")) {
                System.out.println(request);
                outputStream.writeUTF(scanner.nextLine());
                request = inputStream.readUTF();
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
