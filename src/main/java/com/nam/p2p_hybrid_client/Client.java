/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nam.p2p_hybrid_client;

import com.nam.p2p_hybrid_client.peer.Server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NAM
 */
public class Client implements Runnable {
    Socket clientSocket;
    DataInputStream dis;
    DataOutputStream dos;
    Server ownServer;
    String name;
    

    public Client() {
        try {
            this.clientSocket = new Socket("localhost", 9999);
            dis = new DataInputStream(clientSocket.getInputStream());
            dos = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            sendMessage("HEllo Server");
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (true) {
            
            String messageFromServer;
            try {
                messageFromServer = dis.readUTF();
                switch (messageFromServer) {
                    case "own_server_port":
                        createOwnServer();
                        break;
                    case "your_name":
                        name = dis.readUTF();
                        System.out.println(name);
                    
                }
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
    }
    
    public void sendMessage(String message) throws IOException {
        dos.writeUTF(message);
        dos.flush();
    }

    public void createOwnServer() throws IOException {
        int ownServerPort = Integer.parseInt(dis.readUTF());
        ownServer = new Server(ownServerPort);
    }
    
    
}
