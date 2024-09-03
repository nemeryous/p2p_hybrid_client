/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nam.p2p_hybrid_client.peer;

import com.nam.p2p_hybrid_client.P2p_hybrid_client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NAM
 */
public class ServerSocketController implements Runnable {

    Socket connectedSocket;
    DataInputStream dis;
    DataOutputStream dos;
    String name;

    public ServerSocketController(Socket socket) throws IOException {
        connectedSocket = socket;
        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        while (true) {
            String message;
            try {
                message = dis.readUTF();
//                System.out.println(message);
                switch (message) {
                    case "list_of_file":
                        handleEventListOfFile();
                        break;
                    case "response_download_file":
                        handleResponseDownloadFile();
                    default:
                        System.out.println(message);
                }
            } catch (IOException ex) {
                Logger.getLogger(ServerSocketController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void handleEventListOfFile() {
        try {
            Integer lengthOfFolder = Integer.valueOf(dis.readUTF());
            String peerName = dis.readUTF();
            System.out.println(peerName + ": Do dai cua share folder la: " + lengthOfFolder);

            for (int i = 0; i < lengthOfFolder; i++) {
                System.out.println(peerName + ": " + dis.readUTF());
            }
        } catch (IOException ex) {
            System.out.println("Loi o handleEventListOfFile: " + ex);
        }
    }

    public void sendMessage(String message) {
        try {
            dos.writeUTF(message);
            dos.flush();
        } catch (IOException ex) {
            System.out.println("Loi o sendMessage method: " + ex);
            Logger.getLogger(ServerSocketController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Socket getConnectedSocket() {
        return connectedSocket;
    }

    public void setConnectedSocket(Socket connectedSocket) {
        this.connectedSocket = connectedSocket;
    }

    public DataInputStream getDis() {
        return dis;
    }

    public void setDis(DataInputStream dis) {
        this.dis = dis;
    }

    public DataOutputStream getDos() {
        return dos;
    }

    public void setDos(DataOutputStream dos) {
        this.dos = dos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private void handleResponseDownloadFile() {
        try {
            String fileName = dis.readUTF();
//            File file = new File(fileName);
            FileOutputStream fos = new FileOutputStream(P2p_hybrid_client.FOLDER_DOWNLOAD+fileName);
            System.out.println(P2p_hybrid_client.FOLDER_DOWNLOAD+fileName);
            Integer count;
            byte[] bytes = new byte[8096];
            Integer fileLength = Integer.valueOf(dis.readUTF());
            while (fileLength > 0 && (count = dis.read(bytes)) > 0) {
                
                fos.write(bytes, 0, count);
                fileLength -= count;
                System.out.println(fileLength);
            }
            System.out.println("Tai file thanh cong");
            fos.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerSocketController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
