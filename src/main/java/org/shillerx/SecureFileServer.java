package org.shillerx;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SecureFileServer {
    public static void main(String[] args) {
        int port = 5001;
        String fileToSend = "feedbackopgave/src/main/java/org/shillerx/file.md"; 

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Serveren lytter på port " + port);

            // opretter et socket til den client der connecter, 
            //Det gør at den kan håndtere flere brugere sekventielt
            while (true) { 
                Socket clientSocket = serverSocket.accept();
                System.out.println("Klient tilsluttet: " + clientSocket.getInetAddress());

                sendFile(fileToSend, clientSocket);

                clientSocket.close();
                System.out.println("Forbindelse lukket.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //metode til at sende en fil. Den kan filpasses filePath
    private static void sendFile(String filePath, Socket socket) {
        // indlæser den fil vi skal bruge
        File file = new File(filePath);

        // Filen skal eksistere
        if (!file.exists()) {
            System.out.println("Filen findes ikke: " + filePath);
            return;
        }

        // bruger FileInputStream til at oversætte den til bytes
        try (FileInputStream fis = new FileInputStream(file);

            // indlæser socket til clienten med en buffer
             BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream())) {

            // array til alle bytes (Max 4Gb) det lidt overkill here. 
            byte[] buffer = new byte[4096];
            int bytesRead;
            // sender bytes til clienten via FileInputStream (-1 er en stopString)
            while ((bytesRead = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            //renser BufferedOutputStream for at undgå datalæk og spare plads
            bos.flush();
            System.out.println("Filen " + file.getName() + " er sendt til klienten.");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
