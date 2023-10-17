package be.marble.blog.threads._01_WhyThreads.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class GetRenderer implements Runnable {

    private static final int BLOCK_SIZE=1024;
    private static final long DELAY=100;
    private final Socket clientConnection;

    public GetRenderer(final Socket clientConnection) {
        this.clientConnection = clientConnection;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
            String request = reader
                    .readLine()
                    .split(" ")[1]
                    .substring(1);
            System.out.println("Rendering "+request);
            InputStream is = getClass().getClassLoader().getResourceAsStream(request);
            copy(is,clientConnection.getOutputStream());
            is.close();
            clientConnection.close();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private void copy(InputStream is, OutputStream os) throws IOException, InterruptedException {
        byte[] buffer = new byte[BLOCK_SIZE];
        int bytesRead ;
        while ((bytesRead = is.read(buffer))>0) {
            Thread.sleep(DELAY);
            os.write(buffer,0,bytesRead);
        }
    }
}
