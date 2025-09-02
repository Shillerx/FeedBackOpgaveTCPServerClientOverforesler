package org.shillerx;

import java.io.*;
import java.net.Socket;

public class SecureFileClient {
    public static void main(String[] args) {

        // Statiske værdier
        String serverIP = "127.0.0.1"; // også kendt som localhost
        int port = 5001;
        String outputFile = "downloadedDocument.md";

        // opretter en TCP forbindelse til serveren
        try (Socket socket = new Socket(serverIP, port);
            //modtager bits fra serveren. Der er en buffer omkring det socket
             BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());

             // laver en fil. Navnet er defineret under statiske værdier
             FileOutputStream fos = new FileOutputStream(outputFile)) {

            // Laver et array til bits vi modtager fra serveren. 
            byte[] buffer = new byte[4096];
            
            // opretter en int som vi skal bruge til at tælle hvor mange bits vi har modtaget.
            // Serveren sender -1 når den er færdig med at sende bits.
            int bytesRead; 
            while ((bytesRead = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }

            System.out.println("Filen er modtaget og gemt som: " + outputFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
