package be.marble.blog.threads._01_WhyThreads;

import be.marble.blog.threads._01_WhyThreads.utils.GetRenderer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class _02_MultiThreadedServer {

    public static void main(String[] args) {
        System.out.println("Now launch a browser and go to http://localhost/index.html");
        try {
            ServerSocket server = new ServerSocket(80);
            while (true) {
                final Socket clientConnection = server.accept();
                GetRenderer renderer = new GetRenderer(clientConnection);
                Thread t = new Thread(renderer);
                t.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
