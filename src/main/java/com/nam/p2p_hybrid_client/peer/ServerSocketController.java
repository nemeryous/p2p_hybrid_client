/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nam.p2p_hybrid_client.peer;

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
public class ServerSocketController implements Runnable{
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
                System.out.println(message);
            } catch (IOException ex) {
                Logger.getLogger(ServerSocketController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    public void sendMessage(String message) throws IOException {
        dos.writeUTF(message);
        dos.flush();
    }
    
    
}
